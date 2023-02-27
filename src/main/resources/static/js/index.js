let stompClient;
let member;
let channel;
let channelMember = {};

$(async ()=>{
	await init();
	setEvent();
});

async function init(){
	$('#channels').empty();
	
	// 로그인 사용자 정보 얻기
	member = getMemberObjet();
	
	// 채널 정보들 얻기
	let channels = await getChannel();
	channel = channels[0]; 
	channels.forEach(channel => {
		let $channelNode = $(getChannelNode(channel));
		$channelNode.data('channel', channel);
		$('#channels').append($channelNode);
	});
	
	// 채널 UI 클릭 이벤트 설정.
	$('#channels li').on('click', async (e)=>{
	 	let selectedChannel = $(e.target).closest("li").data('channel');
		
		if(selectedChannel.id === channel.id) return;
		
		channel = selectedChannel;
		
		//채널에 속한 메시지 들고오기
		let messges = await getMessages(channel.id);
		console.log(messges);
		
		//들고온 메시지 렌더링
		$('#chat').empty();
		messges.forEach(message => {
			let chatNode = getChatNode(message);
			$('#chat').prepend(chatNode);
		});
		setScrollHeight();	
	});
	
	let channelMembers = await getChannelMembers();
	channelMembers.forEach((cm) => {
		let key = cm.id;
		channelMember[key] = cm;
	});
	
		
	stompConnect();
}

async function getChannelMembers(){
	let res = await fetch(`/api/v1/members/${channel.id}`);
	return await res.json();
}

function getMemberObjet(){
	let member = {};
	
	let memberInfo = $('#memberInfo').serializeArray();
	memberInfo.forEach(object => {
		let key = object['name'];
		let value = object['value']
		
		if(key === 'id') value = Number(value);
	
		member[key] = value; 
	});
	
	return member
}

function setEvent(){
	//입장 버튼
	$('#modal button').on('click', async (e)=>{
		e.preventDefault();
		
		let name = $('#nameInput').val();
		
		if(name.trim() === '') return;
		
		//멤버 생성
		await createMember(name);
		
		pageInit();
		
		$('#modal').hide();
		$('#container').show();
		connect();
	});
	
	// send 버튼
	$('#send').on('click', sendMessage);
	
	// Enter -> 전송
	$("#sendMessage").on("keyup", (e)=>{
		e.preventDefault();
		if(e.keyCode === 13){
			if (!e.shiftKey){
				sendMessage();
			}			
		}
	});
	
	$('#channelAddBtn').on('click', async ()=>{
		let channelName = $('#channelName').val();
		createChannel(channelName);
		$('#channelAddModal').modal('toggle');
		await init();
		
	});
}

async function createChannel(channelName){
	let res = await fetch(`/api/v1/chat/messages/channels`,{
		method : 'post',
		header: {
			"Content-Type" : "application/json"
		},
		body : JSON.stringify(
			{
				"channelName" : channelName,
				"memberId" : member.id
			}
		)
	});
}

async function getMessages(channelId){
	let res = await fetch(`/api/v1/chat/messages/${channelId}`);
	return await res.json();
}

async function getChannel(){
	let res = await fetch("/api/v1/chat/channels");
	return await res.json();
}

async function createMember(name){
	let res = await fetch("/api/v1/members", {
		method : "post",
		headers:{
			"Content-Type" : "application/json"
		},
		body : JSON.stringify(
			{
				"name" : name
			}
		)
	});
	
	res = await res.json();
	member = res;
	console.log(member);
}

function createChannel(){
	fetch("/api/v1/chat/channels", {
		method : "post",
		headers:{
			"Content-Type" : "application/json"
		},
		body : JSON.stringify(
			{
				"channelName" : member.name,
				"memberId" : member.id
			}
		)
	})
	.then((res) => res.json())
	.then((res) => console.log(res));
}

function stompConnect() {
	var socket = new SockJS('/ws');
	stompClient = Stomp.over(socket);

	stompClient.connect({}, connectionSuccess);
}

function connectionSuccess() {
	stompClient.subscribe('/sub/chat/' + channel.id, onMessageReceived);
}


function sendMessage(){
	let message = $('#sendMessage').val();
	
	if(message.trim() === '') {
		$('#sendMessage').val("");
		return;
	}
	
	stompClient.send("/pub/chat/" + channel.id, {}, JSON.stringify({
		senderId : member.id,
		channelId : channel.id,
		content : message
	}))
	
	$('#sendMessage').val("");
}

function onMessageReceived(message){
	let chatMessage = JSON.parse(message.body);
	chatMessage.content = chatMessage.content.replaceAll('\n', '<br/>'); 

	let chatNode = getChatNode(chatMessage)
	
	$('#chat').append(chatNode);
	
	setScrollHeight();
}

function getChatNode(chatMessage){
	if(chatMessage.senderId == member.id){
		return getMeChatNode(chatMessage);
	}else{
		return getYouChatNode(chatMessage);
	}
}

function setScrollHeight(){
	let scrollHeight = $('#chat').prop('scrollHeight');
	$('#chat').scrollTop(scrollHeight);
}



function getYouChatNode(chatMessage){
	return `<li class="you d-flex">
                    <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/1940306/chat_avatar_01.jpg" class="rounded-circle me-3" width="50px" height="50px" alt="">
                    <div class="content vw-100">
                        <div class="entete">
                            <span class="status blue"></span>
                            <h2>${chatMessage.senderName}</h2>
                            <h3>10:12AM, Today</h3>
                        </div>
                        <div class="message">
                            ${chatMessage.content}
                        </div>
                    </div>
                </li>`;
}

function getMeChatNode(chatMessage){
	return `<li class="me">
                <div class="entete">
                    <h3>10:12AM, Today</h3>
                    <h2>${chatMessage.senderName}</h2>
                    <span class="status blue"></span>
                </div>
                <div class="message">
                    ${chatMessage.content}
                </div>
            </li>`;
}

function getChannelNode(channel){
	return `<li>
                <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/1940306/chat_avatar_01.jpg" alt="">
                <div>
                    <h2>${channel.name}</h2>
                    <h3>
                        <span class="status orange"></span>
                        offline
                    </h3>
                </div>
            </li>`;
}
let stompClient;
let member;
let currChannel;
let channelMember = {};

$(async ()=>{
	await init();
	setEvent();
});

async function init(){
	// 로그인 사용자 정보 얻기
	member = getMemberObjet();
	await initChannel();
	stompConnect();
}

function setEvent(){
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
		await createChannel(channelName);
		$('#channelAddModal').modal('toggle');
		await init();
	});
	
	// 채널 UI 클릭 이벤트 설정.
	$('#channels').on('click', 'li.channel', async (e)=>{
	 	let selectedChannel = $(e.target).closest("li").data('channel');
		
		if(selectedChannel.id === currChannel.id) return;
		
		currChannel = selectedChannel;
		
		//채널에 속한 메시지 들고오기
		let messges = await getMessages(currChannel.id);
		
		//들고온 메시지 렌더링
		$('#chat').empty();
		messges.forEach(message => {
			let chatNode = getChatNode(message);
			$('#chat').prepend(chatNode);
		});
		setScrollHeight();
		
		//badge 제거	
		const badge = $(e.target).closest("li").find('.noRead');
		badge.text('+0');
		badge.hide();
	});
	
	// 채널 나가기
	$('#exitChannel').on('click', async ()=>{
		await exitChannel();
		await initChannel();
	});
}

async function initChannel(){
	// 채널 정보들 얻기
	let channels = await getChannel();
	if(channels.length > 0){
		$('#noChannel').hide();
		$('#chatWrap').show();
		
		currChannel = channels[0]; 
		$('.channel').remove();
		
		channels.forEach(channel => {
			let $channelNode = $(getChannelNode(channel));
			$channelNode.data('channel', channel);
			$('#publicChannel').after($channelNode);
		});	
	}
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

async function createChannel(channelName){
	await fetch(`/api/v1/chat/channels`,{
		method : 'post',
		headers: {
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

async function exitChannel(){
	await fetch(`/api/v1/chat/channel/${currChannel.id}/member/${member.id}`,{ method : 'delete' });
}

async function getMessages(channelId){
	let res = await fetch(`/api/v1/chat/messages/${channelId}`);
	return await res.json();
}

async function getChannel(){
	let res = await fetch(`/api/v1/chat/channels?memberId=${member.id}`);
	return await res.json();
}

function stompConnect() {
	var socket = new SockJS('/ws');
	stompClient = Stomp.over(socket);

	stompClient.connect({}, connectionSuccess);
}

function connectionSuccess() {
	$.each($('.channel'), (idx, value) => {
		const channel = $(value).data('channel'); 
		stompClient.subscribe('/sub/chat/' + channel.id, onMessageReceived);	
	});
}


function sendMessage(){
	let message = $('#sendMessage').val();
	
	if(message.trim() === '') {
		$('#sendMessage').val("");
		return;
	}
	
	stompClient.send("/pub/chat/" + currChannel.id, {}, JSON.stringify({
		channelId : currChannel.id,
		content : message
	}))
	
	$('#sendMessage').val("");
}

function onMessageReceived(message){
	let chatMessage = JSON.parse(message.body);
	chatMessage.content = chatMessage.content.replaceAll('\n', '<br/>'); 
	
	if(chatMessage.channel.id === currChannel.id){
		let chatNode = getChatNode(chatMessage)
		$('#chat').append(chatNode);
		setScrollHeight();	
	}else{
		$.each($('.channel'), (_, value) => {
			const channel = $(value).data('channel');
			
			if(chatMessage.channel.id === channel.id){
				const badge = $(value).find('.noRead');
				const count = Number(badge.text().substring(1)); 
				$(value).find('.noRead').text(`+${count + 1}`);
				badge.show();
				return false;
			}
		});
	}
}

function getChatNode(chatMessage){
	if(chatMessage.sender.id == member.id){
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
                            <h2>${chatMessage.sender.name}</h2>
                            <h3>${chatMessage.createdAt.split(" ")[1]}</h3>
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
                    <h3>${chatMessage.createdAt.split(" ")[1]}</h3>
                    <h2>${chatMessage.sender.name}</h2>
                    <span class="status blue"></span>
                </div>
                <div class="message">
                    ${chatMessage.content}
                </div>
            </li>`;
}

function getChannelNode(channel){
	return `<li class="channel">
                <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/1940306/chat_avatar_01.jpg" alt="">
                <div>
                    <h2>${channel.name}</h2>
                    <h3>
                        <span class="status orange"></span>
                        offline
                    </h3>
                </div>
                <span class="badge bg-danger noRead">+4</span>
            </li>`;
}
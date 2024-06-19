const toggleSidebar=()=>{
	if($(".sidebar").is(":visible")){
		$(".sidebar").css("display" ,"none");
		$(".content").css("margin-left","0%");
	} 
	
	else{
		$(".sidebar").css("display" ,"block");
		$(".content").css("margin-left","20%");
	}
}

const Search=()=>{
	let query=$("#query").val();
	 if(query==""){
		$(".primarycontainer").hide();
	 }
	 else{
		let url=`http://localhost:8282/search/${query}`
		fetch(url).then((res)=>{
			return res.json();
		}).then((data)=>{
			let text=`<div class='list-group'>`;
	data.forEach(contact => {
		text+=`<a href="/user/viewContact/+${contact.cId}" class='list-group-item list-group-item-action'> ${contact.name} </a>`
	});

	text+=`</div>`;
	$(".primarycontainer").html(text);
	$(".primarycontainer").show();
		})	
	 }
}
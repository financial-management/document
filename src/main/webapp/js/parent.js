
var page_heigth=0;

var default_heigth=700;
		    
function setHeigth(heigth){
	page_heigth=heigth+10;
    $("iframe").height(page_heigth);
}


function findBodyHeigth(){
	return $(document.body).height();
}

function setParntHeigth(){
	 var heigth =findBodyHeigth();
	 if(parent['setHeigth']){
		 parent['setHeigth'](heigth);
	 }
}
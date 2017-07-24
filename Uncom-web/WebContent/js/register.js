   function closeHtml()
			{
				setTimeout(function(){
						document.getElementById('login_page_row').innerHTML=""
				},3000);
				$('a').click(function(){
				document.getElementById('login_page_row').innerHTML=""
			})
			}
			
			

function register_email_check(){
        var checked_sum=false;
        var checked_sum_2=false;
        var checked_sum_3=false;
        var checked_sum_4=false;
        var checked_sum_5=false;
        var sprovince=$("#register_input_province").val();
        var stown=$("#register_input_town").val();
        var sarea=$("#register_input_area").val();
        var input_username=$("#register_input_username").val();
     	var input_email=$('#register_input_email').val();
     	var input_password=$('#register_input_password').val();
        var input_repassword=$('#register_input_repassword').val();
        var male=null;
        if(input_email.indexOf('@')==-1){
       	  
       	    $('#register_email_div').attr('class',"form-group-sm has-error");
       	  $('#register_input_email').val("");
       	  $('#register_input_email').attr('placeholder','邮箱格式错误');
       	 checked_sum=false;
        }
        else{
        	 checked_sum=true;
        	  $('#register_email_div').attr('class',"form-group-sm has-success");
        }
        if(!input_password){
        	$('#register_password_div').attr('class','form-group-sm has-error')
        	$('#register_input_password').attr('placeholder','密码输入不为空');
        }
        else{
        	$('#register_password_div').attr('class','form-group-sm has-success')
        }
        if(input_password!=input_repassword||!input_password||!input_repassword){
        	checked_sum_2=false;
        	$('#register_input_repassword').val("")
        	if('input_password!=input_repassword'){
        		$('#register_input_repassword').attr('placeholder','密码输入错误');
        	}
        	if(!input_repassword){
        		$('#register_input_repassword').attr('placeholder','密码输入不为空');
        	}
        	$('#register_repassword_div').attr('class','form-group-sm has-error')
        }
        else{
        	checked_sum_2=true;
        	$('#register_repassword_div').attr('class','form-group-sm has-success')
        }
        if(!input_username){
     		$("#register_username_div").attr('class','form-group-sm has-error')
     		
     	}
        else{
        	checked_sum_3=true;
        	$("#register_username_div").attr('class','form-group-sm has-success')
        }
        if($("#register_input_area").val()=='地区'||!$('#register_input_province').val()||$('#register_input_province').val()=='省(自治区)'||!$("#register_input_town").val()||$("#register_input_town").val()=='市'||!$("#register_input_area").val()){
        	document.getElementById("selecter_addr_label").innerHTML='请选择学校地址'
        }else{
        	document.getElementById("selecter_addr_label").innerHTML='学校地址'
        	checked_sum_4=true
        }

        if($("input:radio:checked").val()){
        	checked_sum_5=true;
        	if($("input:radio:checked").val()=="boy"){
        		male=1;
        	}else{
        		male=0;
        	}
        	$("#male_radio").attr("class","radio col-xs-12 col-lg-8 col-lg-offset-2 has-success")
        }else{
        	checked_sum_5=false;
        	$("#male_radio").attr("class","radio col-xs-12 col-lg-8 col-lg-offset-2 has-error")
        }

        if(checked_sum==true&&checked_sum_2==true&&checked_sum_3==true&&checked_sum_4==true&&checked_sum_5==true){
        	$.post("/Uncom/register_Register.action",{username:input_username,password:input_password,email:input_email,sex:male,sprovince:sprovince,stown:stown,sarea:sarea},function(date){
        		
        		/*
        		 * 定义返回值数值: 
        		 * 1 昵称重复
        		 * 2 邮箱重复
        		 * 
        		 */
        		  
        		if(date==1){
        			
        		}
        		else if(date==2){
        			
        		}
        		else{
        			document.getElementById("login_page_row").innerHTML=date
        			closeHtml()
        			console.debug("closehtm begin")
        		}
        	})
        }
      
        
        
     	
     }
            	

function produce_addr(){
	console.log("dsy"+dsy)
	var buttonlength=dsy.Items['0'];
	var form=document.getElementById('addr_selecters_body');
	var i=0;
	for(var temp in buttonlength){
		(function(){
			var te=temp;
		var temp2=document.createElement("button");
       temp2.setAttribute('class','btn btn-group-lg');
       temp2.style.position='relative';
       temp2.style.margin='5px';
       temp2.innerHTML=buttonlength[i];
       temp2.addEventListener('click',function(){produce_addr_2('0',te);},false)
  
       form.appendChild(temp2);
       i++;
		})();
       
	}
	$('#addr_selecters').on('hide.bs.modal', function () {
		form.innerHTML='';
		return 0;
      ;})
}


function produce_addr_2(elem,elem2 ){
	var temp=elem+'_'+elem2;
	var buttonleng=dsy.Items['0'];
	var button_1=document.getElementById('register_input_province');
	var button_2=document.getElementById('register_input_town');
	button_1.setAttribute('value',buttonleng[elem2]);
	button_2.checked;
	var form=document.getElementById('addr_selecters_body');
	form.innerHTML="";
	var buttonlen= dsy.Items[elem+'_'+elem2];
	var i=0;
	for(var tep in buttonlen){
		(function(){
			var te=tep;
		var temp2=document.createElement("button");
       temp2.setAttribute('class','btn btn-group-lg');
       temp2.style.position='relative';
       temp2.style.margin='5px';
       temp2.innerHTML=buttonlen[i];
       form.appendChild(temp2);
       temp2.addEventListener('click',function(){ produce_addr_3(elem+'_'+elem2,te)})
       i++;
		})()
		
	};
	/*$('#addr_selecters').on('hide.bs.modal', function () {
     form.innerHTML='';
		return 0;})*/
}



function produce_addr_3(elem,elem2){
	var form=document.getElementById('addr_selecters_body');
	var button_2=document.getElementById('register_input_town');
	var button_3=document.getElementById('register_input_area');
	var buttonleng=dsy.Items[elem];
	var buttonleng_2=dsy.Items[elem+'_'+elem2];
	var i=0;
	button_2.setAttribute('value',buttonleng[elem2]);
	form.innerHTML='';
	
	for(var temp in buttonleng_2){
		(function(){
			var tem=temp;
			var temp2=document.createElement("button");
       temp2.setAttribute('class','btn btn-group-lg');
       temp2.style.position='relative';
       temp2.style.margin='5px';
       temp2.innerHTML=buttonleng_2[i];
       form.appendChild(temp2);
       temp2.addEventListener('click',function(){ produce_addr_4(elem+'_'+elem2,tem)})
       i++;
			
		})()
	}
	/*$('#addr_selecters').on('hide.bs.modal', function () {
      form.innerHTML='';
		return 0;})*/
   
}

function  produce_addr_4(elem,elem2){
	var main=document.getElementById('addr_selecters')
	var button_3=document.getElementById('register_input_area');
	var main=document.getElementById('addr_selecters')
	var buttonleng=dsy.Items[elem];
	var buttonleng_2=dsy.Items[elem];
	var form=document.getElementById('addr_selecters_body');
	button_3.checked;
	button_3.setAttribute('value',buttonleng_2[elem2]);
	form.innerHTML='';
	$("#addr_selecters").modal("hide");
	return 0;	
}


function Checking(){
	var password=document.getElementById('register_input_password');
	var repassword=document.getElementById('register_input_repassword');
	
	if(password!=repassword){
		alert('密码错误!');
	}
}





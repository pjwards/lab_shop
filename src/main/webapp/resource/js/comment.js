function confirmation(question) {
        	    var defer = $.Deferred();
        	    alert(question);
        	    $('<div></div>')
        	        .html(question)
        	        .dialog({
        	        	autoOpen: true,
        	          	modal: true,
        	          	title: 'Confirmation',
        	          	buttons: {
        	          	       "Yes": function () {
        	          	           defer.resolve("true");
        	          	           $(this).dialog("close");
        	          	       },
        	          	       "No": function () {
        	          	             defer.resolve("false");
        	          	             $(this).dialog("close");
        	          	       		}
        	          	       },
        	          	       close: function () {
        	        	           $(this).remove();
        	          	            }
        	         });
        	   return defer.promise();
        	}

$("a#addWish").click(function(){
	var question = "Do you want to add this in wishlist?";
	alert(question);
	confirmation(question).then(function (answer) {
	    var ansbool = (String(answer) == "true");
	    if(ansbool){
	    	var data = $("a#addWish").attr("vals");
			var arr = data.split('/');
			alert(ansbool);
			$.ajax({
				type:"POST",
				url:"<%=request.getContextPath()%>/user/addWishlist.do",
				data:{ email : arr[0], check : ansbool, no : arr[1] },
				success:function(result){
					if(result === "400"){
						alert("Already existed");
					}else if(result === "200"){
						alert("Added in wishlist");
					}
				}
			});
	    }
	});			
});

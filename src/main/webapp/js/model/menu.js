$(function() {
	
	$('#menuForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			//验证的是控件上的name属性的名称
			name : {
				validators : {
					notEmpty : true
				}
			},
			url : {
				validators : {
					notEmpty : true
				}
			},
			icon : {
				validators : {
					notEmpty : true
				}
			}
		}
	});
});

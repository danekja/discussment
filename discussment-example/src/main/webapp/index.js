
$('.collapse').on('shown.bs.collapse', function(){
    $(this).parent().find(".glyphicon-plus").removeClass("glyphicon-plus").addClass("glyphicon-minus");
}).on('hidden.bs.collapse', function(){
    $(this).parent().find(".glyphicon-minus").removeClass("glyphicon-minus").addClass("glyphicon-plus");
});


$(document).ready(function() {
    $('#privateDiscussion').click(function() {

        $("#passwordDiscussion").toggle(this.checked);

        if($('#privateDiscussion').prop('checked')) {
            $("#pass").prop('required',true);
        } else {
            $("#pass").prop('required',false);
        }
    });
});

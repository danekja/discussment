$(document).ready(function() {

    $('.collapse').on('shown.bs.collapse', function(){
        $(this).parent().find(".glyphicon-plus").removeClass("glyphicon-plus").addClass("glyphicon-minus");
    }).on('hidden.bs.collapse', function(){
        $(this).parent().find(".glyphicon-minus").removeClass("glyphicon-minus").addClass("glyphicon-plus");
    });

    $('#privateDiscussion').click(function() {

        $("#passwordDiscussion").toggle(this.checked);

        if($('#privateDiscussion').prop('checked')) {
            $("#password").prop('required',true);
        } else {
            $("#password").prop('required',false);
        }
    });
});

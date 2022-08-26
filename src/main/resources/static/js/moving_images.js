$(document).ready(function(event) {
    var first_image = true;
    var f_image_url = $('#hidden_image1').text();
    var s_image_url = $('#hidden_image2').text();
    console.log(f_image_url);
    console.log(s_image_url);
    $('.img1').css('background-image','url(' + f_image_url + ')');
    $('.img2').css('background-image','url(' + s_image_url + ')');


    jQuery('.img1').hover(function() {
        jQuery('#my_image').attr("src", f_image_url);
    });
    jQuery('.img2').hover(function() {
        jQuery('#my_image').attr("src", s_image_url);
    });

    $('#my_image').on({
        'click': function(){
            if(first_image){
                $('#my_image').attr('src', f_image_url);
                first_image = false;
            }else{
                $('#my_image').attr('src', s_image_url);
                first_image = true;
            }
        }
    });
});
$(document).ready(function (){

    $("#addToCart").on("click", function (e){
        addToCart();
    });
});

function addToCart(){
    productQty = $('#productQty').text();
    num =  parseInt($('.cart_items_number').text());

    $('.cart_items_number').text(num + 1);


    let _url = "/cart/add/" + productId + "/" + productQty + "/";

    $.ajax({
        type: "POST",
        url: _url
    });

    if(isNaN(num)){
        $(".cart_items_number1").css("display", "inline-block");
        $(".cart_items_number1").css("padding", "0.15em");
        $(".cart_items_number1").css("background", "red");
        $(".cart_items_number1").css("color", "white");
        $(".cart_items_number1").css("border-radius", "50%");
        $(".cart_items_number1").css("width", "1.7em");
        $(".cart_items_number1").css("height", "1.7em");
        $(".cart_items_number1").css("font-size", ".6em");
        $(".cart_items_number1").css("margin-left", "-1.8em");
        $(".cart_items_number1").css("margin-top", "-0.7em");
    }
    $("#alert_item_added").css("display", "block");

}
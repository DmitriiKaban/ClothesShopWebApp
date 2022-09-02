$(document).ready(function (){

    $("#addToCart").on("click", function (e){
        addToCart();
    });
});

function addToCart(){
    productQty = $('#productQty').text();
    num =  parseInt($('.cart_items_number').text());
    //alert(num);

    $('.cart_items_number').text(num + 1);
    if(isNaN(num)){
        $('.cart_items_number').css("display", "inline-block");
    }

    let _url = "/cart/add/" + productId + "/" + productQty + "/";

    $.ajax({
        type: "POST",
        url: _url,
        // beforeSend: function (xhr){
        //     xhr.setRequestHeader(header, token);
        // }

        // have to add the function
    });
    $("#alert_item_added").css("display", "block");

    // .done(function(response){
    //     $("#modalTitle").text("Shopping cart");
    //     $("#modalBody").text("");
    //     $("#modalWindow").modal();
    // }).fail(function(response){
    //     $("#modalTitle").text("Shopping cart");
    //     $("#modalBody").text("Error while adding product to shopping cart.");
    //     $("#modalWindow").modal();
    // });
}
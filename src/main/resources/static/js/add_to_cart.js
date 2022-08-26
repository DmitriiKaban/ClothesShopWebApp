$(document).ready(function (){

    $("#addToCart").on("click", function (e){
        addToCart();
    });
});

function addToCart(){
    // productId = $('#productId').text();
    productQty = $('#productQty').text();

    let _url = "/cart/add/" + productId + "/" + productQty + "/";

    console.log(header + " " + token);
    alert(productQty + " items were added to cart");
    $.ajax({
        type: "POST",
        url: _url,
        // beforeSend: function (xhr){
        //     xhr.setRequestHeader(header, token);
        // }

        // have to add the function
    });
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
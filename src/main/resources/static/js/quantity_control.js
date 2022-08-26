$(document).ready(function (){
    $(".minusButton").on("click", function(e){
        e.preventDefault();
        productId = $(this).attr("pid");
        qtyInput = $("#quantity" + productId);
        newQty = parseInt(qtyInput.val() ) - 1;
        if(newQty > 0) qtyInput.val(newQty);
    });
    $(".plusButton").on("click", function(e){
        e.preventDefault();
        productId = $(this).attr("pid");
        qtyInput = $("#quantity" + productId);
        newQty = parseInt(qtyInput.val() ) + 1;
        if(newQty < 6) qtyInput.val(newQty);
    });
});
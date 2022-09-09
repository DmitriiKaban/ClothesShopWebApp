$(document).ready(function(){

    $(".minusButton").on("click", function(e){
        e.preventDefault();
        decreaseQuantity($(this));
    });
    $(".plusButton").on("click", function(e){
        e.preventDefault();
        increaseQuantity($(this));
    });

    $(".link-remove").on("click", function(e){
        e.preventDefault();
        removeFromCart($(this));
    });

    updateTotal();
});

function removeFromCart(link){
    url = link.attr("href");

    $.ajax({
        type: "POST",
        url: url
    }).done(function(response){
        if(response.includes("removed")){
            rowNumber = link.attr("rowNumber");
            removeProduct(rowNumber);
            updateTotal();
        }
    }).fail(function (){
       alert("Removing failed");
    });
}

function removeProduct(rowNumber){
    rowId = "row" + rowNumber;
    $("#" + rowId).remove();
}

function decreaseQuantity(link){
    productId = link.attr("pid");
    qtyInput = $("#quantity" + productId);
    newQty = parseInt(qtyInput.val() ) - 1;
    if(newQty > 0) {
        qtyInput.val(newQty);
        updateQuantity(productId, newQty);
    }
}
function increaseQuantity(link){
    productId = link.attr("pid");
    qtyInput = $("#quantity" + productId);
    newQty = parseInt(qtyInput.val() ) + 1;
    if(newQty < 6) {
        qtyInput.val(newQty);
        updateQuantity(productId, newQty);
    }
}

function updateQuantity(productId, quantity){
    let _url = "/cart/update/" + productId + "/" + quantity;

    console.log(_url);

    $.ajax({
        type: "POST",
        url: _url
    }).done(function (newSubtotal){
        console.log(newSubtotal)
        updateSubtotal(newSubtotal, productId);
        updateTotal();
    }).fail(function (){
        console.log("Failed to update subtotal");
    });
}

function updateSubtotal(newSubtotal, productId){
    $("#subtotal" + productId).text(newSubtotal);
    console.log(newSubtotal);
}

function updateTotal(){
    total = 0.0;

    $(".productSubtotal").each(function (index, element){
        total = total + parseFloat(element.innerHTML);
    });

    $(".totalAmount").text("$" + total);
}
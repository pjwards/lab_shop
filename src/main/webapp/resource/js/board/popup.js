/**
 * Created by seodonghyeon on 5/13/15.
 */

;(function($) {
    // DOM Ready
    $(function() {
        // Binding a click event
        // From jQuery v.1.7.0 use .on() instead of .bind()
        $('#popup').bind('click', function(e) {
            // Prevents the default action to be triggered.
            e.preventDefault();
            // Triggering bPopup when click event is fired
            $('#element_to_pop_up').bPopup({
                content:'ajax', //'ajax', 'iframe' or 'image'
                contentContainer:'.content',
                loadUrl:contextPath+'/goods/list.do?s=product'
            });
        });
    });
})(jQuery);

function delGoods(id) {
    var addTrId = '#goods_id_' + id;
    var price = parseInt($('#price_' + id).text());
    var totalPrice = parseInt($('#total_price').prop('value'));
    $(addTrId).remove();
    $('#total_price').prop('value',totalPrice-price);

    if(parseInt($('#total_price').prop('value'))<0){
        $('#total_price').prop('value',0);
    }
};
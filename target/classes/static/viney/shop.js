var currentPage = getUrlParam("page");
if (currentPage == null) {
    currentPage = 1;
}

//搜索参数
var searchParam = getUrlParam("searchParam");
if (searchParam == null) {
    searchParam = "";
} else {
    $("#searchContent").html(searchParam);
    searchParam = "&searchParam=" + searchParam;
}

//排序参数
var selectParam = getUrlParam("selectParam");
if (selectParam == null) {
    selectParam = "";
} else {
    console.log(selectParam);
    $("#selectSort").find("option[value = '"+ selectParam +"']").attr("selected",true);
    $('#selectSort').niceSelect('update');
    selectParam = "&selectParam=" + selectParam;
}

//排序
function selectProduct() {
    var selectP = $("#selectSort").val();
    if (selectP == "Nothing") {
        window.location.href = 'shop.html?page=1' + searchParam;
    } else {
        window.location.href = 'shop.html?page=1' + searchParam + "&selectParam=" + selectP;
    }
}

$.ajax({
    async: true,
    dataType: "json",
    type: "GET",
    url: "/goods/showGoodsByPage?page=" + currentPage + searchParam + selectParam,
    success: function (res) {
        var goodsList = res.list;
        for (var i = 0; goodsList.length > i; i++) {
            $("#goodsListId").append(`
                    <div class="col-md-4">
                        <div class="single-product mb-25">
                            <div class="product-img img-full">
                                <a href="single-product.html?goods=${goodsList[i].id}">
                                    <img src="${goodsList[i].cover}" alt="${goodsList[i].name}">
                                </a>
                                <span class="onsale">-${100 - goodsList[i].discount}% OFF</span>
                            </div>
                            <div class="product-content">
                                <h2><a href="single-product.html?goods=${goodsList[i].id}">${goodsList[i].name}</a></h2>
                                <div class="product-price">
                                    <div class="price-box">
                                        <span class="price">RD ${goodsList[i].originalPrice}</span>
                                        <span class="regular-price">RD ${goodsList[i].discountPrice}</span>
                                    </div>
                                    <div class="add-to-cart">
                                        <a href="single-product.html?goods=${goodsList[i].id}">Details</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                `)
        }

        var lastOne = false;
        for (var j = 1; j <= res.totalPage; j++) {
            if (res.curPage === j) {
                if (res.curPage === res.totalPage) {
                    lastOne = true;
                }
                $("#pageId").append(`
                        <li class="active"><a>${res.curPage}</a></li>
                    `);
            } else {
                $("#pageId").append(`
                        <li><a href="../shop.html?page=${j}${searchParam}${selectParam}">${j}</a></li>
                    `);
            }
        }
        if (lastOne == false) {
            $("#pageId").append(`
                        <li><a href="../shop.html?page=${res.nextOne}${searchParam}${selectParam}"><i class="fa fa-angle-double-right"></i></a></li>
                    `);
        }

    }
});

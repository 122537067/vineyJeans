$(function(){
    var productId = getUrlParam("goods");
    var clipBoardContent=this.location.href;

    if(productId === null){
        return;
    }
    $.ajax({
        async:true,
        dataType:"json",
        type:"GET",
        url:"/goods/selectGoodsById?id=" + productId,
        success:function(res){
            var product = res.data;
            $("#titleId").html("Viney Jeans- " + product.name);
            var productImg = removeStartEnd(product.picture);
            productImg = productImg.split(",");

            //商品大图
            $("#singleImgId").append(`
                <div class="tab-pane fade show active" id="product0">
                    <div class="product-large-thumb img-full">
                        <div class="easyzoom easyzoom--overlay">
                            <a href="${product.cover}">
                                <img src="${product.cover}" alt="" width="470px" overflow="hidden">
                            </a>
                            <a href="${product.cover}" class="popup-img venobox"
                               data-gall="myGallery"><i class="fa fa-search"></i></a>
                        </div>
                    </div>
                </div>
            `);

            //商品轮播图
            $("#productMenuId").append(`
                <div class="product-details-img">
                    <a class="active" data-toggle="tab" href="#product0"><img
                            src="${product.cover}" alt="" style="width: 68px;"></a>
                </div>
            `);

            for(var i=0; productImg.length>i;i++){
                //商品大图
                $("#singleImgId").append(`
                    <div class="tab-pane fade" id="product${i+1}">
                        <div class="product-large-thumb img-full">
                            <div class="easyzoom easyzoom--overlay">
                                <a href="${productImg[i]}">
                                    <img src="${productImg[i]}" alt="" width="470px" overflow="hidden">
                                </a>
                                <a href="${productImg[i]}" class="popup-img venobox"
                                   data-gall="myGallery"><i class="fa fa-search"></i></a>
                            </div>
                        </div>
                    </div>
                `);

                //商品轮播图
                $("#productMenuId").append(`
                    <div class="product-details-img">
                        <a data-toggle="tab" href="#product${i+1}"><img
                                src="${productImg[i]}" alt="" style="width: 68px;"></a>
                    </div>
                `);

            }

            /*-----------------------------------
            Single Product Slide Menu Active
            --------------------------------------*/
            $('.product-tab-menu').slick({
                prevArrow: '<i class="fa fa-angle-left"></i>',
                nextArrow: '<i class="fa fa-angle-right slick-next-btn"></i>',
                slidesToShow: 4,
                responsive: [
                    {
                        breakpoint: 1200,
                        settings: {
                            slidesToShow: 3,
                            slidesToScroll: 3
                        }
                    },
                    {
                        breakpoint: 992,
                        settings: {
                            slidesToShow: 4,
                            slidesToScroll: 4
                        }
                    },
                    {
                        breakpoint: 480,
                        settings: {
                            slidesToShow: 3,
                            slidesToScroll: 3
                        }
                    }
                ]
            });
            $('.product-tab-menu a').on('click',function(e){
                e.preventDefault();

                var $href = $(this).attr('href');

                $('.product-tab-menu a').removeClass('active');
                $(this).addClass('active');

                $('.single-product-img .tab-pane').removeClass('active show');
                $('.single-product-img '+ $href ).addClass('active show');

            })

            /*加载商品信息*/
            console.log(clipBoardContent);
            $("#productNameId").append(product.name);
            $("#productIdId").append(product.id);
            $("#discountPriceId").append("RD " + product.discountPrice);
            $("#originalPriceId").append("RD " + product.originalPrice);
            $("#introId").append(product.intro);
            $("#supCateId").append(product.supCategoryName);
            $("#cateId").append(product.categoryName);
            $("#shareUrl").append(clipBoardContent);
            $("#detailId").append(product.description);

        }
    });
});
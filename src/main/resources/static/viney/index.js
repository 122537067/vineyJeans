$(function(){

    //轮播图加载
    $.ajax({
        async:false,
        dataType:"json",
        type:"GET",
        url:"/banner/showBanner",
        success:function(bannerList){
            for(var i=0; bannerList.length>i; i++) {
                $('.hero-slider').append(`
                    <div class="single-slider" style="background-image: url(${bannerList[i].picture})">   
                        <div class="slider-progress"></div>
                        <div class="container">
                            <div class="hero-slider-content">
                                <h1>${bannerList[i].title}</h1>
                                <div class="slider-border"></div>
                                <p>${bannerList[i].intro} </p>
                                <div class="slider-btn">
                                    <a href="single-product.html?goods=${bannerList[i].goodsId}">on Sale!<i class="fa fa-chevron-right"></i></a>
                                </div>
                            </div>
                        </div>
                    </div>
                `);
            }
            /*----------
                     Hero Slider Active
                ------------------------------*/
            $('.hero-slider').owlCarousel({
                smartSpeed: 500,
                nav: true,
                loop: true,
                animateOut: 'fadeOut',
                animateIn: 'fadeIn',
                autoplay: true,
                navText: ['prev', 'next'],
                items:1,
            })
        }
    });

    //特色加载
    $.ajax({
        async:false,
        dataType:"json",
        type:"GET",
        url:"/feature/showFeature",
        success:function(featureList){
            for(var j=0;featureList.length>j;j++) {
                $("#featureId").append(`
                    <div class="col-lg-4 col-md-6">
		                <div class="single-feature mb-35">
		                    <div class="feature-icon">
		                        <img src="${featureList[j].picture}">
		                    </div>
		                    <div class="feature-content">
		                        <h3>${featureList[j].title}</h3>
		                        <p>${featureList[j].intro}</p>
		                    </div>
		                </div>
		            </div>
                `);
            }
        }
    });

    //促销加载
    $.ajax({
        async:false,
        dataType:"json",
        type:"GET",
        url:"/goods/showMostDiscount",
        success:function(goodsList){
            for(var k=0;goodsList.length>k;k++) {
                $("#saleProductId").append(`
                    <div class="col-md-4">
                        <div class="single-product mb-25">
                            <div class="product-img img-full">
                                <a href="single-product.html?goods=${goodsList[k].id}">
                                    <img src="${goodsList[k].cover}" alt="">
                                </a>
                                <span class="onsale">-${100 - goodsList[k].discount}% OFF</span>
                            </div>
                            <div class="product-content">
                                <h2><a href="single-product.html?goods=${goodsList[k].id}">${goodsList[k].name}</a></h2>
                                <div class="product-price">
                                    <div class="price-box">
                                        <span class="price">RD ${goodsList[k].originalPrice}</span>
                                        <span class="regular-price">RD ${goodsList[k].discountPrice}</span>
                                    </div>
                                    <div class="add-to-cart">
                                        <a href="single-product.html?goods=${goodsList[k].id}">Detail</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                `);
            }
            /*----------
                 Hot Deal Slider Active
            ------------------------------*/
            $('.count-down-area').slick({
                slidesToShow: 1,
                slidesToScroll: 1,
                arrows: false,
                fade: true,
                asNavFor: '.offer-slider'
            });
            $('.offer-slider').slick({
                slidesToShow: 3,
                slidesToScroll: 1,
                asNavFor: '.count-down-area',
                dots: true,
                centerMode: true,
                arrows: false,
                infinite: true,
                centerPadding: '0px',
                focusOnSelect: true,
                responsive: [
                    {
                        breakpoint: 1024,
                        settings: {
                            slidesToShow: 3,
                        }
                    },
                    {
                        breakpoint: 768,
                        settings: {
                            slidesToShow: 1,
                        }
                    },
                    {
                        breakpoint: 480,
                        settings: {
                            slidesToShow: 1,
                        }
                    }
                ]
            });
        }
    });


});

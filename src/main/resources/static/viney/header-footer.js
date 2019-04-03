$(function(){
    $.ajax({
        async:false,
        dataType:"json",
        type:"GET",
        url:"/headerFooter/showHeaderFooter",
        success:function(headerFooter){
            $("#headerLogoId").attr("src",headerFooter.topLogo);
            $("#companyNameId").append(headerFooter.name);
            $("#copyrightId").append(headerFooter.copyright);
            $("#contactId").append(`<div class="col-lg-3 col-md-6">
		                        <div class="single-footer-widget mb-30">
		                            <div class="footer-logo">
		                                <a href="index.html"><img alt="Logo" src="${headerFooter.footerLogo}"></a>
		                            </div>
		                        </div>
		                    </div>`);

            $.ajax({
                async:false,
                dataType:"json",
                type:"GET",
                url:"/contact/showAllContact",
                success:function(res){
                    for(var i=0; res.data.length>i; i++){
                        $("#contactId").append(`
                        <div class="col-lg-3 col-md-6">
		                        <div class="single-footer-widget mb-30">
		                            <div class="footer-info">
		                                <div class="icon">
		                                    <img src="${res.data[i].picture}">
		                                </div>
		                                <p>${res.data[i].title} :<br/> ${res.data[i].intro}</p>
		                            </div>
		                        </div>
		                    </div>
                        `)
                    }
                }
            });
        }
    });
});
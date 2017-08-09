<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="./base/header.jsp" />



    <div class="jumbotron">
        <h1>功能区</h1>
        <p class="lead"> 点击下面的按钮，后台记录操作</p>
        <p>
            <a class="btn btn-lg btn-success log-button" href="#" role="button">1</a>
            <a class="btn btn-lg btn-default log-button" href="#" role="button">2</a>
            <a class="btn btn-lg btn-danger log-button" href="#" role="button">3</a></p>
    </div>

    <script>
        $(document).ready(function(){
            $(".log-button").click(function(){
                console.log("用户点击："+this.innerText);
                $.ajax({
                    url:"./user/log",
                    method:"POST",
                    data:{logMessage:"user click button "+this.innerText},
                    success:function(r){
                        console.info(r);
                    }
                });
            });
        });
    </script>

<jsp:include page="./base/footer.jsp"/>

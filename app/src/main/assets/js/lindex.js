//打开设置界面
function openSettings(){
    app.openSetting()
}

//打开侧边菜单栏
function openLeftDrawer(){
  app.openDrawer();
}

//一键复制文本
function copytxt(str){
   app.copyAll(str);
}



//控制输入框提示语切换
function changeInputHints(data){
   // controlPreviewHidden();
    var input=$et('search');
    //判断传入类型；
    var type=data.getAttribute('datas')
    var button=$et('selectop');

    var inputhead='请输入';
    switch(type){
        case 'hz':
        case 'cmnp':
        case 'cmnipa':
            controlAppendsVisibleOrNot(0);
            break;
        //case 'moi':
        case 'uni':
        case 'hkipa':
            controlAppendsVisibleOrNot(1);
            break;
        default:
            break;
    }
    input.setAttribute('placeholder',inputhead+data.innerHTML);
    button.setAttribute('datas',type);
    button.innerHTML=data.innerHTML;
}

//控制附加查询组件(第二个筛选布局)显示
function controlAppendsVisibleOrNot(tyep){
    var willVisibleView=$et('filters');
    let les;
    while((les=willVisibleView.firstChild)){
     les.remove();
    }

   //0为不显示；1为显示；
   switch(tyep){
    case 0:
        break;
    case 1:
        categoryLayout(willVisibleView)
        break;
   }

}


/*
<li>
   <a class="dropdown-item" onclick="changeCategoryHint(this)" href="javascript:void(0)" datas="jetd_gajin_hk">粤东嘉应客语</a>
</li>
*/
//下拉框模板化
function dropdown_template_(left_side_name,button_id,datas,button_name,lis){
    var dropdaown_template=
    `
    <div class="row g-3">
        <div class="col-4">
            <a class="text-black-75 btn p-2">${left_side_name}</a>
        </div>
        
        <div class="col-8">
            <div class="dropdown">
                <button class="btn dropdown-toggle p-2 text-black-75" data-bs-toggle="dropdown" style="letter-spacing: 2px;" id="${button_id}" datas="${datas}">
                ${button_name}
                </button>
                <ul class="dropdown-menu border-0" id="cates">
                    ${lis}
                </ul>
            </div>
        </div>
    </div>
    `
    return dropdaown_template
   }


//查询条件布局代码
function categoryLayout(willVisibleView){
        var lis=`<li>
                   <a class="dropdown-item" onclick="changeCategoryHint(this)" href="javascript:void(0)" datas="jetd_gajin_hk">粤东嘉应客语</a>
                 </li>`;

         //当有额外需求时可在此载入数据；默认为嘉应客语；
        willVisibleView.innerHTML=dropdown_template_("查询条件","detailhakkas","jetd_gajin_hk","粤东嘉应",lis)
        AsyncCategories()
}

//控制查询条件显示
function changeCategoryHint(datas){
    var detailbutton=$et('detailhakkas');

    var tag=datas.getAttribute('datas');
    detailbutton.setAttribute('datas',tag);
    detailbutton.innerHTML=datas.innerHTML;
}



//获取搜索框数据和筛选条件，传递搜索参数
function getInput(){
    var input=$et('search').value.replace(/^\s*|\s*$/g,'');
    var selectop=$et('selectop');
    var type=selectop.getAttribute('datas');
    var isAccess=true;
    var infos=$et('infos')

    switch(type){
        case 'hz':
            var lregex=/[A-Za-z0-9`~@#*()_+<>:"|\/^%&',.·・;=?${}～，、：‘’‵（）。；？“”！]{1,}/g
            if(lregex.test(input)){
                //alert('检测到非法字符，请输入汉字！')
                infos.innerHTML=`<div class="alert alert-info alert-dismissible fade show m-0 p-2" role="alert" id="infod">
                                   检测到非法字符，请输入汉字！
                                 </div>`

            setTimeout(()=>{
                var myAlert = $et('infod')
                var bsAlert = new bootstrap.Alert(myAlert)
                bsAlert.close()
            },2500)
            isAccess=false;
            }
            break;

        case 'uni':
        case 'hkipa':
        case 'cmnp':
        case 'cmnipa':
            var regex=/[\u4e00-\u9fff`~@#*()_+<>:"|・·\/^%&',.;=?$[\]{}～，、：‘’‵（）。；？“”！]{1,}/g
            if(regex.test(input)){
                //alert('检测到非法字符，只能输入字母和数字！')
                infos.innerHTML=`<div class="alert alert-info alert-dismissible fade show m-0 p-1" role="alert" id="infod">
                                    检测到非法字符，只能输入字母和数字!
                                 </div>`

                setTimeout(()=>{
                    var myAlert = $et('infod')
                    var bsAlert = new bootstrap.Alert(myAlert)
                    bsAlert.close()
                },2500)
                isAccess=false
            break;
    }

}

    var url='';
    if(isAccess){
        url=combinationUrl(input,type)
    }
    else url='kull'
    return url
}

//生成请求链接
function combinationUrl(input_value,type){
    var value_= input_value.length==0?'kull':input_value
    var format_=type
    var appendArea_='kull'
    switch(type){
        case 'hkipa':
        case 'moi':
        case 'uni':
            var detailhakka=$et('detailhakkas');
            format_=type
            appendArea_=detailhakka.getAttribute('datas');
            break;
        default:
    }

    //第一个参数value，表示实际输入字符；format表示输入格式；appendArea表示第二层分类；
    var url_template=`?value=${value_}&format=${format_}&appendArea=${appendArea_}`
    //console.log('-->'+url_template)
    //这里写入网站api；
    return url_template;

}


//请求服务器数据，并解析JSON数据
function AlinkService(){
    //获取输入文本，向服务器发送请求
    var url=getInput();
    if(url!='kull'){
        //这里写入网站api；
        var final_url=''
        if (url.includes('format=hz&appendArea=kull')){
            final_url='http://dict.hazukieq.top/searchapis/search_hz'+url;
        }
        else{
            final_url='http://dict.hazukieq.top/searchapis/search_pin'+url;
        }
    
        if(navigator.onLine){
            AsyncJSON(final_url);
        }else{
            setTimeout(()=>{
                returnSkeleton(4,final_url);
            },200);
        }
    }
}


var jos='';
function AsyncJSON(ue){

    if(ue.includes("format=hz&appendArea=kull")){
        returnSkeleton(1,'');
    }else{
        returnSkeleton(2,'');
    }

    //参数url,timeout,success_callback,failed_callback
    sendAjax(ue,2500,
        //请求成功
        (text)=>{
        jos=JSON.parse(text);
        setTimeout(()=>{
        controlResultLayout(jos)
        },100);
    },
        //请求失败
        ()=>{
            if(!navigator.onLine){
                setTimeout(()=>{
                    returnSkeleton(4,ue);
                },200);
            }else{
                setTimeout(()=>{
                    returnSkeleton(3,ue);
               },200);
        }
    });
}


//请求动态客家话分类数据，返回JSON数据
function AsyncCategories(){

    var hks=''
    var uls=$et('cates');
    var body=''

    var tsr=app.requestHkcategories()
    hks=JSON.parse(tsr)
    var dil=hks.names
    var il=hks.codes
    //这里传入解析数据；
    for(var i=0;i<hks.names.length;i++){
    //载入数据节点；
    let hk_category=dil[i];
    body+=`<li>
            <a class="dropdown-item" onclick="changeCategoryHint(this)" href="javascript:void(0)" datas="${il[i]}">${hk_category}</a>
            </li>`;
    }
    uls.innerHTML=body;

}


//控制搜索布局返回结果
function controlResultLayout(jso){
    //控制显示模式，1为汉字模式，0为音节模式；jso为原生jsons，利于不同模式解析不同数据；
    //var chs=generateJson(jso);
    switch(jso.input_type){
        case "0":
            resultLayout.innerHTML=HanzCards(jso);
            break;
        case "1":
            resultLayout.innerHTML=PinzCards(jso);
            break;
    }
}



function card_tepmplate_(type,reloadTag,reloadTagUrl,body_code){
    var card_skeleton_one=
            `<div class="card m-2 border-0">
                <div class="card-header m-2 ">
                    <div class="row g-2">
                        <div class="col-3 align-self-center">
                            <p class="ps-2 pe-2 card_primary" style="font-Size:46px;">...</p>
                        </div>
                        <div class="col-9">
                            <p class="m-1">笔画：<span class="card_text">...</span></p>
                            <p class="m-1">部首：<span class="card_text">...</span></p>
                            <p class="m-1">普通话：<span class="card_text">...</span></p>
                        </div>
                    </div>
                </div>
                <div class="card-footer border-0 p-0 ">
                    <div class="row mb-2 p-0">
                        <div class="col-8"></div>
                        <div class="col-4 p-0">
                            <a class="btn card_primary p-2" href="${reloadTagUrl}">${reloadTag}</a>
                        </div>
                    </div>
                </div>
            </div>`;

    var card_skeleton_two=
        `<div class="card m-2 border-0">
            <div class="card-header m-2 ">
                <div class="row">
                    <div class="col">
                        <h5 class="card_text">
                            <span class="card_primary text-bold">${reloadTag}</span>
                        </h5>
                    </div>
                </div>
            </div>
            <div class="card-body  pt-0">
                ${body_code}
            </div>
        </div>`;

        switch(type){
            case 0:
                return card_skeleton_one;
            case 1:
                return card_skeleton_two;
        }
}


//加载完成前展示骨架屏幕；加载失败时也时如此；
function returnSkeleton(mode,url){
    var resultLayout=$et('resultLayout');
    var loadTag='数据加载中';
    var reloadTag='hkapp://reload_/'+url;
    
    //mode分为1，2，3三种；第一种用于显示汉字卡片，第二种用于显示音节卡片，第三种用于显示网络加载失败时的卡片；
    var skeleton_one=card_tepmplate_(0,loadTag,reloadTag,'')
    
    var skeleton_tow=card_tepmplate_(1,loadTag,reloadTag,'<p>......<p>')
    
    var skeleton_three=card_tepmplate_(1,'数据加载失败','','<a class="btn btn-primary" href="'+reloadTag+'">重新加载数据</a>')

    var skeleton_failure=card_tepmplate_(1,'无法连接服务器','',
            '连接断开了，请检查网络是否畅通.....<div class="w-100"></div><a class="btn btn-primary mt-2" href="'+reloadTag+'">刷新重试</a>')

    switch(mode){
        case 1:
            resultLayout.innerHTML=skeleton_one;
            break;
        case 2:
            resultLayout.innerHTML=skeleton_tow;
            break;
        case 3:
            resultLayout.innerHTML=skeleton_three;
            break;
        case 4:
            resultLayout.innerHTML=skeleton_failure
            break;
    }
}

//生成汉字搜索布局
function HanzCards(chs){
    var card=ResultCard(chs);
    return card;
}

//生成音节搜索布局
function PinzCards(chz){

    var pincard=ResultPinCard(chz);
    return pincard;
}


//单字卡片布局,注意chract是对象
function ResultCard(chract){

    if(chract.hz=='...'){
        var chard_failure=
            `<div class="card m-2 border-0">
                <div class="card-header m-2">
                    <h5 class="card_text">加载失败</h5>
                </div>
                <div class="card-footer border-0 p-0">
                    <p class="text-danger p-2">数据库找不到相关数据哦~</p>
                </div>
            </div>`

    return chard_failure;

    }else{
        var hz=chract.hz;
        var bh=chract.bh;
        var cmn=chract.cmn_p;
        var ps=chract.ps
        var detail_link=chract.link;

        var chard=
    `<div class="card m-2 border-0">
        <div class="card-header m-2 ">
            <div class="row g-2">
                <div class="col-3 align-self-center">
                    <p class="ps-2 pe-2 card_primary" style="font-Size:46px;">${hz}</p>
                </div>
                <div class="col-9">
                    <p class="m-1">笔画：<span class="card_text">${bh}</span></p>
                    <p class="m-1">部首：<span class="card_text">${ps}</span></p>
                    <p class="m-1">普通话：<span class="card_text">${cmn}</span></p>
                   
                </div>
            </div>
        </div>
        <div class="card-footer border-0 p-0 ">
            <div class="row mb-2 p-0">
                <div class="col-8"></div>
                <div class="col-4 p-0">
                    <a class="btn card_primary p-2" href="${detail_link}">更多</a>
                </div>
            </div>
        </div>
    </div>`;

    return chard;
    }

}


//单音节卡片布局
function ResultPinCard(pinract){

    //音节JSON格式：origin--用户搜索音节；pins--命中音节数组:pin命中具体音节；hanzs--pins下属数组，相同音节的所有汉字;links--pins下属数组，相同音节的所有汉字对应数组;
    //拼音数组,注意拼音数组中具体音节下又附带汉字数组和链接数组;
    var pins=pinract.pins;

    //获取jsons中搜索原始拼音,用origin标识；
    var Targetpin=pinract.origin;

    var cardBody_main='';

    //判断json中的pin值是否为空
    if (pins==null){
        cardBody_main+='<div><p class="text-danger pt-2">数据库找不到相关数据哦~</p></div>';
    }else{
        //按声调排序：冒泡排序法
        var cycledNums=pins.length
        for(var f=0;f<cycledNums-1;f++){
            for(var k=0;k<cycledNums-f-1;k++){
                var ftr=parseInt(pins[k].pin.charAt(pins[0].pin.length-1))
                var ktr=parseInt(pins[k+1].pin.charAt(pins[0].pin.length-1))
                if(ftr>ktr){
                    var temp=pins[k];
                    var tr=pins[k+1]
                    pins[k+1]=temp
                    pins[k]=tr
                }
            }
        }

        for(j in pins){
            //详情查看链接；默认请求url为：api_url+"?value=空&format=hz"; APP拦截协议链接为app://detail?value=空&format=hz;
            var links=pins[j].links;
            //拼音下所属具体汉字数组
            var hanzs=pins[j].hanzs;
            var tag= pins[j].pin;


            //一键复制功能，复制所有相同读音的汉字，返回分隔开、形式完好的复制文本；
            //Accordion布局
            var main_h=
            `<div class="accordion mt-2">
                <div class="accordion">
                    <div class="accordion-item border-0">
                        <button class="accordion-button btn p-3 collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#${tag}" aria-expanded="false">
                            <h6>${tag}</h6>
                        </button>
                        <div class="accordion-collapse mt-0 collapse" id="${tag}" style="">
                            <div class="accordion-body p-2">
                                <ul class="list-inline" id="ul_${tag}">
                                ${generateList(hanzs,links)}
                                </ul>
                                <div class="col offset-9 p-0">
                                    <a class="acopy p-0" onclick="returnCopys(${tag})">
                                        <small>复制文本</small>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>`
            cardBody_main+=main_h
        }
    }
    
    //整个卡片布局
    var pinard=
    `<div class="card m-2 border-0">
        <div class="card-header m-2">
            <div class="row">
                <div class="col">
                    <h5 class="card_text">
                        <span class="card_primary text-bold">${Targetpin}</span>&nbsp;的所有集合
                    </h5>
                </div>
            </div>
        </div>
        
        <div class="card-body pt-0">
        ${cardBody_main}
        </div>
    </div>
    `
    return pinard;
}

//生成单个item代码
function generateList(hanzs,qinks){
    var reR='';
    for(s in hanzs){
        reR+='<li class="list-inline-item p-2"><a class="aslink pt-2 pb-2" href="hkapp://search_/?s='+qinks[s]+'">'+hanzs[s]+'</a></li>';
    }
    return reR;
}


//复制文本函数
function returnCopys(main){
   // console.log('returnCopys argument-->'+main.id);
    var ulq=$et("ul_"+main.id);
    var lis=$tag(ulq,'li');
    //console.log('getlis-->'+lis.length);
    var copys='复制文本：';

    for(var i=0;i<lis.length;i++){
        copys+=lis[i].innerText+"\t";
    }
    //传入文本数据到APP端复制文本函数
    copytxt(copys);
}
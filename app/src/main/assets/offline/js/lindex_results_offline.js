window.onload=card_skeleton(0,'');
callLoad();
//callServer('http://43.142.122.229/lindex.html?s=一')


//通知APP加载数据
function callLoad(){
    app.offlineLoad()   
}



var eurl=''
//传递参数
function getUrl(url){
    eurl=url;
    setTimeout(function(){
            callServer(eurl);
        },200)
}

function card_tepmplate_(type,reloadTag,body_code){
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
                            <p class="m-1">异体字：<span class="card_text">...</span></p>
                        </div>
                    </div>
                </div>
                <div class="card-footer border-0 p-0 ">
                    <div class="row mb-2">
                        <div class="col ms-3 p-2">
                            ${reloadTag}
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

//骨架屏幕加载
function card_skeleton(mode,reloadTag){
    var cardLayout_root=$et('cardLayout');
    cardLayout_root.style.fontFamily="han";

    var alls=['汉语字典']
    var alls_c=['hd']
    var accordions=''
    for(var i=0;i<1;i++){
        var all=alls[i]
        var all_c=alls_c[i]
        var accordio=
            `<div class="accordion mt-2">
                <div class="accordion">
                    <div class="accordion-item border-0">
                        <button class="accordion-button btn p-3 collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#${all_c}" aria-expanded="false"><h6>${all}</h6></button>
                        <div class="accordion-collapse collapse mt-0" id="${all_c}"><div class="accordion-body">......</div>
                    </div>
                </div>
            </div>`
            accordions+=accordio
    }
    var normal_skeleton_footer=
            `<div class="row m-2">
                <div class="col p-0">
                ${accordions}
                </div>
            </div>`;

    var normal_skeleton=card_tepmplate_(0,'数据加载中...','')+normal_skeleton_footer
    var skeleton_failed=`
            <div class="card m-2 border-0">
                <div class="card-header m-2 ">
                    <div class="row">
                        <div class="col">
                            <h5 class="card_text">
                                <span class="card_primary text-bold">数据加载失败...</span>
                            </h5>
                        </div>
                    </div>
                </div>
                <div class="card-body  pt-0">
                    <a class="btn btn-primary" href="hkapp://reload_/${reloadTag}">重新加载数据</a>
                </div>
            </div> `

    switch(mode){
        case 0:
            cardLayout_root.innerHTML=normal_skeleton;
            break;
        case 1:
            cardLayout_root.innerHTML=skeleton_failed;
            break;
        default:
            cardLayout_root.innerHTML=normal_skeleton;
            break;
    }
    
}

//组装卡片布局
function mergeCardLayout(sjo){
    var arrays=window.app.returnAlls().split(',');
    var asr=[];
    for(var d=0;d<arrays.length;d++){
        var sd=parseInt(arrays[d])
        asr.push(sd);
    }
    var cardLayout_root=$et('cardLayout');
    var cards=generateCard(sjo);
    var foots=returnLists(sjo,asr);
    cardLayout_root.innerHTML=cards+foots;
}

//卡片布局生成代码,根据sjo数据生成具体数据布局代码
function generateCard(sjo){
    var hz=sjo.hz;
    var bh=sjo.bh;
    var ps=sjo.ps;
    var cmnp=(sjo.cmn_p!='')?sjo.cmn_p:'缺';
    var vas=sjo.va;
    var vas_ext=(vas!='')?`<p class="m-1">异体字：<span class="card_text">${vas}</span></p>`:'';
    var cardBody_content=''+allHakkaspagers(sjo);
    var card_=
                `<div class="card m-2 border-0">
                    <div class="card-header ">
                        <div class="row align-items-center">
                            <div class="col-3">
                                <figure class="text-center"><p class="card_primary" style="font-Size:46px;" id="bighz">${hz}</p></figure>
                            </div>
                            <div class="col-9">
                                <p class="m-1">笔画：<span class="card_text">${bh}</span></p>
                                <p class="m-1">部首：<span class="card_text">${ps}</span></p>
                                <p class="m-1">普通话：<span class="card_text">${cmnp}</span></p>
                                ${vas_ext}
                            </div>
                        </div>
                    </div>

                    <div class="card-body p-0" class="card_text">
                        <div class="row g-0">
                            <div class="col">
                                <div class="list-group p-3" id="areas">${cardBody_content}</div>
                            </div>
                        </div>
                    </div>
                </div>`;


    return card_;
}

//生成各地客家话读音分布布局代码
//各地客家话读音页面
//sound为json模型，包含detailHakka和sou
function allHakkaspagers(sound){

    var pin=sound.jetd_gajin_hk

    var pagerTab="";
    if(pin!=''){
        pagerTab=
             `<div class="list-group-item normalText border-0 p-0 m-0"><a onclick="CheckSource()" class="float-end">数据来源&nbsp;</a>
              </div>
              <div class="list-group-item normalText border-0 p-0 m-0">
                粤东嘉应：<span class="aslin p-0 m-0" style="letter-spacing:0.6px;">${pin}<span/>
              </div>`
    }else{
        pagerTab=`
                <div class="list-group-item normalText border-0 p-0 m-0">
                    <div class="p-2"></div>
                </div>`
    }
    return pagerTab;
}





function CheckSource(){
    var source_ext=`
        嘉应客语：<font color="#d63384">1926年客法字典</font>
                    <br/>
                    <font color="#495050">声调</font>
                    <small>
                    <br/>
                    <b>第1声</b>&nbsp;44&nbsp;阴平
                    <br/>
                    <b>第2声</b>&nbsp;11&nbsp;阳平
                    <br/>
                    <b>第3声</b>&nbsp;31&nbsp;上声
                    <br/>
                    <b>第5声</b>&nbsp;52&nbsp;去声
                    <br/>
                    <b>第7声</b>&nbsp;1&nbsp;阴入
                    <br/>
                    <b>第8声</b>&nbsp;5&nbsp;阳入
                    <br/><br/>
                    </small>`
    app.showsource(source_ext)
}


//额外卡片，比如说文解字等等根组件代码,根据aso进行解析生成布局
var direction=['','vertext']
var vertainer=['','verticaline']
var swfonts=['swfont-lg','swfont-md','swfont-sm']
var unifonts=['unifont-lg','unifont-md','unifont-sm']
var lettersp=['letters-lg','letters-md','letters-sm']
var lineheight=['lh-lg','','lh-sm']
var bgcolor=['bgcolor-white','bgcolor-yellow','bgcolor-gray']
function returnLists(aso,arrals){

    var hdext='';
    var hd=hd!=undefined?'':aso.hd.replace(/[\r\n]/g,'<br/>');

    if(!hd=='') hdext+=generateLists('hd',"汉语大字典",hd,vertainer[arrals[10]],direction[arrals[10]],unifonts[arrals[11]],lettersp[arrals[12]],lineheight[arrals[13]],bgcolor[arrals[14]]);

    var List_='<div class="row m-2"><div class="col pb-2 pe-0 ps-0">'
    var List_end='</div></div>';
    return List_+hdext+List_end;
}


//生成说文、康熙、汉语字典等解释卡片，注意这几个卡片是独立存在的
//bg背景颜色，mode竖排或横排容器，vertext竖排或横排，lt字距，lhw行距
function generateLists(list_id,list_name,content,mode,vertext,ft,lt,lhw,bg){
    all=ft+' '+lt+' '+lhw
    var List_item=`<div class="accordion mt-2">
                        <div class="accordion">
                            <div class="accordion-item border-0">
                                <button class="accordion-button btn p-3 collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#${list_id}" aria-expanded="false">${list_name}</button>
                                <div class="accordion-collapse collapse mt-0 ${bg}" id="${list_id}">
                                    <div class="accordion-body ${mode}"><p class="${vertext} ${all}">${content}<p></div>
                                </div>
                            </div>
                        </div>
                    </div>`;
    return List_item;
}


//请求服务器发送数据，并进行解析返回生成布局
function callServer(url){
    //console.log("已经启动....");
    app.AsyncJSON()
}


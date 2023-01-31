//调用APP函数更新开关
setTimeout(function(){
    app.callSwics();
},100)


function callics(){
    app.callSwics();
}

//返回上一个界面
function back2last(){
    app.finic();
}


//加载储存值，并更新设置选项
function loadSwitchs(offline,tabchange,night){
   // console.log(preview+","+offline+","+tabchange)
   // updateSwitch('pin_switch',preview);
    updateSwitch('offline_switch',offline);
    //updateSwitch('tab_switch',tabchange);
   // updateSwitch('night_switch',night)
}


//设置开关属性
function setSwic(iti,sortedIndex){
    var lswitch=$et(iti);
    var sele=1;
    if(lswitch.checked==true){
        sele=1;
    }else if(lswitch.checked==false){
        sele=0;
    }
    app.setSwitch(sortedIndex,sele);
}

//更新设置开关属性,0是关闭,1是开启
function updateSwitch(id,str){
    var lswitch=$et(id);
    if(str==0) lswitch.checked=false;
    else lswitch.checked=true;
}



/***<-------设置列表布局代码生成区--------->***/


//生成详情界面中说文、康熙、汉典卡片具体布局
function moreSettings(tag,tag_attach){
    var root=$et(tag);
    var root_attach=$et(tag_attach);
    var root_attach_icon=$et(tag_attach+'img');
    var isVisible=root.getAttribute('expanded','false')

    
    if(isVisible=='true'){
        root.classList.add('visually-hidden');
        root.setAttribute('expanded','false')

        switch(tag_attach){
            case 'sw':
            case 'kx':
            case 'hd':
                root_attach.classList.remove('border-0')
                break;
            case 'ltc':
                break;
        }
        root_attach.classList.remove('text-danger')
        root_attach_icon.src='dist/img/chevright.svg'
    }else if(isVisible=='false'){
        root.classList.remove('visually-hidden')
        root.setAttribute('expanded','true')
        switch(tag_attach){
            case 'sw':
            case 'kx':
            case 'hd':
                root_attach.className+=' border-0 text-danger'
                break;
            case 'ltc':
                root_attach.className+=' text-danger'
                break;
        }
        
        root_attach_icon.src='dist/img/chevron-down.svg'
    }

}

//生成说文设置等的条目布局
function generateBigLists(toor,id,drt,ft,lt,lh,bg){
    var root=$et(toor);
    var statics=['文字排列方向','字体大小','文字间距','文字行距','背景颜色']
    

    var diretions=['横向','纵向']
    var  fontSize=['大','标准','小',]
    var letterSpacing=['大','标准','小',]
    var lineHeight=['大','标准','小',]
    //var paragraghHeight=['大','标准','小',]
    var bgColor=['白色','纸黄色','灰色']
    var root_id=2;

    switch(id){
        case 'sw':
            root_id=2;
            break;
        case 'kx':
            root_id=3;
            break;
        case 'hd':
            root_id=4;
            break;
        case 'ltc':
            root_id=5;
            break;
    }

    var lis=''
    lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start list-group-item-action normalText" style="font-size: 14px;" onclick="openDialog(5,'+root_id+',2)">'+statics[0]+'<div class="me-2 ms-auto text-primary"><span id="'+id+'drt">'+diretions[drt]+'<span></div></li>'
    lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start list-group-item-action normalText" style="font-size: 14px;" onclick="openDialog(3,'+root_id+',3)">'+statics[1]+'<div class="me-2 ms-auto text-primary"><span id="'+id+'ft">'+fontSize[ft]+'<span></div></li>'
    lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start list-group-item-action normalText" style="font-size: 14px;" onclick="openDialog(3,'+root_id+',4)">'+statics[2]+'<div class="me-2 ms-auto text-primary"><span id="'+id+'lt">'+letterSpacing[lt]+'<span></div></li>'
    lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start list-group-item-action normalText" style="font-size: 14px;" onclick="openDialog(3,'+root_id+',5)">'+statics[3]+'<div class="me-2 ms-auto text-primary"><span id="'+id+'lh">'+lineHeight[lh]+'<span></div></li>'
//  lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start list-group-item-action normalText" style="font-size: 14px;" onclick="openDialog(3,'+root_id+',6)">'+statics[4]+'<div class="me-2 ms-auto text-primary"><span id="'+id+'pl">'+paragraghHeight[pl]+'<span></div></li>'
    lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start list-group-item-action normalText" style="font-size: 14px;" onclick="openDialog(4,'+root_id+',6)">'+statics[4]+'<div class="me-2 ms-auto text-primary"><span id="'+id+'bg">'+bgColor[bg]+'<span></div></li>'

    var uls='<div class="list-group border-0 rounded-0 ps-3">'
    var uls_end='</div>'
    root.innerHTML=uls+lis+uls_end
}



//生成详情界面中客家、普通话读音设置布局代码
function basicSettings(ta,ta_attah){
    var root=$et(ta)
    var root_attach=$et(ta_attah);
    var root_attach_icon=$et(ta_attah+'img');
    var isVisible=root.getAttribute('expanded','false')


    if(isVisible=='true'){
        root.classList.add('visually-hidden');
        root.setAttribute('expanded','false');
        if(ta_attah=='hk') root_attach.classList.remove('border-0');
        root_attach.classList.remove('text-danger');
        root_attach_icon.src='dist/img/chevright.svg'
        //else{}
        
    }else if(isVisible=='false'){
        root.classList.remove('visually-hidden');
        root.setAttribute('expanded','true');
        if(ta_attah=='hk') root_attach.classList.add('border-0');
        root_attach.classList.add('text-danger');
        root_attach_icon.src='dist/img/chevron-down.svg'
    }

}

//生成客家、普通话读音设置具体条项
function generateLists(rot,vi,to,type){
    var root=$et(rot);
    var uls_head='<div class="list-group border-0 rounded-0 ps-3">'
    var uls_end='</div>'
    var lis=''
    
    switch(type){
        case 0:
            lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start list-group-item-action normalText" style="font-size: 14px;" onclick="openDialog(0,0,0)">'+'拼音显示格式'+'<div class="me-2 ms-auto text-primary"><span id="hk_visibles">'+vi+'<span></div></li>'
            lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start list-group-item-action normalText" style="font-size: 14px;" onclick="openDialog(2,0,1)">'+'声调显示格式'+'<div class="me-2 ms-auto text-primary"><span id="hktone"><span>'+to+'</div></li>'
            break;
        case 1:
            if(vi=='汉语拼音'){
                lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start list-group-item-action normalText" style="font-size: 14px;" onclick="openDialog(1,1,0)">'+'拼音显示格式'+'<div class="me-2 ms-auto text-primary"><span id="cmn_visibles">'+vi+'<span></div></li>'
                lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start list-group-item-action normalText" style="font-size: 14px;" onclick="openDialog(6,1,1)" id="cmntnli">'+'声调显示格式'+'<div class="me-2 ms-auto text-primary"><span id="cmntone">'+to+'</div></li>'
            }else{
                lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start list-group-item-action normalText" style="font-size: 14px;" onclick="openDialog(1,1,0)">'+'拼音显示格式'+'<div class="me-2 ms-auto text-primary"><span id="cmn_visibles">'+vi+'<span></div></li>'
                lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start list-group-item-action normalText" style="font-size: 14px;" onclick="openDialog(2,1,1)" id="cmntnli">'+'声调显示格式'+'<div class="me-2 ms-auto text-primary"><span id="cmntone">'+to+'</div></li>'
            }
            
            break;
    }

    root.innerHTML= uls_head+lis+uls_end;

}





/***<-----保存设置键值，传递数据给APP保存，并实时更新当前条项值等---->***/

//更新单个设置条目的值
function updateSingleValue(id,str){
    var sp=$et(id);
    if(id=='cmn_visibles'){
        changeValues(str)
    }
    sp.innerHTML=str;

}


//加载储存的spValue
function loadStoredValues(t,ta_atth,type){
    basicSettings(t,ta_atth)
    app.loadSingleprams(t,type)
   //generateLists(t,"国际音标","数字型",type)

}

//加载大型spValues
function loadBigStoredValues(toi,ta_at){
    moreSettings(toi,ta_at);
    app.loadBigValues(toi);

}

//调用APP对话框
//key索引值，map映射索引，tag前置标签索引，abbr缩略标签索引
function openDialog(map,tag,abbr){
    var tags=['hk','cmn','sw','kx','hd','ltc']
    var mappingvalue=['hk_visibles','cmn_visibles','tones','typographys','bgcolors','directions','special_tones']
    var abbreviations=['_visibles','tone','drt','ft','lt','lh','bg']

    app.showaloh(0,mappingvalue[map],tags[tag]+abbreviations[abbr]);
    
}

function changeValues(selectedResult){
    var cmn_toneli=$et('cmntnli')
    if(selectedResult=='汉语拼音'){
        cmn_toneli.setAttribute('onclick','openDialog(6,1,1)')
    }else{
        var cmnt=$et('cmntone')
        var t_=cmnt.innerHTML
        if(t_=='标调型'){
            cmnt.innerHTML='数字型'
            app.setParam(0)
        }
        
        cmn_toneli.setAttribute('onclick','openDialog(2,1,1)')
    }
}


//调用APP函数更新开关
//window.
//app.callSwics();
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
    updateSwitch('offline_switch',offline);
    updateSwitch('tab_switch',tabchange);
    updateSwitch('night_switch',night)
}



function setSwic(iti,sortedIndex){
    var lswitch=document.getElementById(iti);
    var sele=1;
    if(lswitch.checked==true){
        sele=1;
    }else if(lswitch.checked==false){
        sele=0;
    }
    app.setSwitch(sortedIndex,sele);
}

//APP设置预览音节模式是否开启
function setpinPreview(){
    var lswitch=document.getElementById('pin_switch');
    var sele=1;
    if(lswitch.checked==true){
        sele=1;
    }else if(lswitch.checked==false){
        sele=0;
    }
    app.setSwitch(0,sele);
}


//APP设置离线模式是否开启
function setOffline(){
    var lswitch=document.getElementById('offline_switch');
    var sele=1;
    if(lswitch.checked==true){
        sele=1;
    }else if(lswitch.checked==false){
        sele=0;
    }
    app.setSwitch(0,sele);
}


//APP设置左右滑动模式是否开启
function setIsCan(){
    var iswitch=document.getElementById('tab_switch');
    var ele=1;

    if(iswitch.checked==true){
        ele=1;
    }else if(iswitch.checked==false){
        ele=0;
    }
    app.setIs(ele);
    
}


//更新设置开关属性,0是关闭,1是开启
function updateSwitch(id,str){
    var lswitch=document.getElementById(id);
    if(str==0) lswitch.checked=false;
    else lswitch.checked=true;
}


//更新设置预览音节模式开关属性,0是关闭,1是开启
function updatelSwitch(str){
    var lswitch=document.getElementById('pin_switch');
    if(str==0) lswitch.checked=false;
    else lswitch.checked=true;
}

//更新设置离线模式开关属性,0是关闭,1是开启
function updateOfflineSwitch(str){
    var lswitch=document.getElementById('offline_switch');
    if(str==0) lswitch.checked=false;
    else lswitch.checked=true;
}

//更新设置中左右滑动页面开关
function updateIsScroll(str){
    var iswitch=document.getElementById('tab_switch');
    if(str==0) iswitch.checked=false;
    else iswitch.checked=true;
}




//生成具体设置布局
function moreSettings(tag,tag_attach){
    var root=document.getElementById(tag);
    var root_attach=document.getElementById(tag_attach);
    var root_attach_icon=document.getElementById(tag_attach+'img');
    var isVisible=root.getAttribute('expanded','false')

    
    if(isVisible=='true'){
        root.classList.add('visually-hidden');
        root.setAttribute('expanded','false')


        if(tag_attach=='ltc'){
            root_attach.classList.remove('rounded-0')
            root_attach.classList.add('rounded-bottom')
        }
        root_attach.classList.remove('border-0')
        root_attach.classList.remove('text-danger')
        root_attach.classList.add('text-white')
        root_attach_icon.src='../dist/img/chevright.svg'
    }else if(isVisible=='false'){
        root.classList.remove('visually-hidden')
        root.setAttribute('expanded','true')
        root_attach.classList.remove('text-white')
        switch(tag_attach){
            case 'ltc':
                root_attach.classList.remove('rounded-bottom')
                root_attach.classList.add('rounded-0')
                break;
        }
        root_attach.className+=' border-0 text-danger'
        root_attach_icon.src='../dist/img/chevron-down.svg'
    }

}

//生成说文设置等的条目布局
function generateBigLists(toor,id,drt,ft,lt,lh,bg){
    var root=document.getElementById(toor);
    var statics=['文字排列方向','字体大小','文字间距','文字行距','背景颜色']
    

    var diretions=['横向','纵向']
    var  fontSize=['大','标准','小',]
    var letterSpacing=['大','标准','小',]
    var lineHeight=['大','标准','小',]
    //var paragraghHeight=['大','标准','小',]
    var bgColor=['白色','纸黄色','灰色','浅黑色']
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
    lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start list-group-item-action grayext" style="font-size: 14px;" onclick="openDialog(5,'+root_id+',2)">'+statics[0]+'<div class="me-2 ms-auto text-primary"><span id="'+id+'drt">'+diretions[drt]+'<span></div></li>'
    lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start list-group-item-action grayext" style="font-size: 14px;" onclick="openDialog(3,'+root_id+',3)">'+statics[1]+'<div class="me-2 ms-auto text-primary"><span id="'+id+'ft">'+fontSize[ft]+'<span></div></li>'
    lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start list-group-item-action grayext" style="font-size: 14px;" onclick="openDialog(3,'+root_id+',4)">'+statics[2]+'<div class="me-2 ms-auto text-primary"><span id="'+id+'lt">'+letterSpacing[lt]+'<span></div></li>'
    lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start list-group-item-action grayext" style="font-size: 14px;" onclick="openDialog(3,'+root_id+',5)">'+statics[3]+'<div class="me-2 ms-auto text-primary"><span id="'+id+'lh">'+lineHeight[lh]+'<span></div></li>'
//    lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start list-group-item-action grayext" style="font-size: 14px;" onclick="openDialog(3,'+root_id+',6)">'+statics[4]+'<div class="me-2 ms-auto text-primary"><span id="'+id+'pl">'+paragraghHeight[pl]+'<span></div></li>'
    lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start grayext" style="font-size: 14px;">'+statics[4]+'<div class="me-2 ms-auto text-primary"><span id="'+id+'bg">'+bgColor[3]+'<span></div></li>'

    var uls='<div class="list-group border-0 rounded-0 ps-3 pb-2 pe-2">'
    var uls_end='</div>'
    root.innerHTML=uls+lis+uls_end
}

//生成具体设置代码
function basicSettings(ta,ta_attah){
    var root=document.getElementById(ta)
    var root_attach=document.getElementById(ta_attah);
    var root_attach_icon=document.getElementById(ta_attah+'img');
    var isVisible=root.getAttribute('expanded','false')


    if(isVisible=='true'){
        root.classList.add('visually-hidden');
        root.setAttribute('expanded','false');
        if(ta_attah=='cmn'){
            root_attach.classList.remove('rounded-0');
            root_attach.classList.add('rounded-bottom')
        }
        root_attach.classList.remove('border-0');
        root_attach.classList.remove('text-danger');
        root_attach.classList.add('text-white')
        root_attach_icon.src='../dist/img/chevright.svg'
    }else if(isVisible=='false'){
        root.classList.remove('visually-hidden');
        root.setAttribute('expanded','true');
        root_attach.classList.add('border-0')
        root_attach.classList.remove('text-white')
        if(ta_attah=='cmn'){
            root_attach.classList.remove('rounded-bottom')
            root_attach.classList.add('rounded-0');
            root.classList.add('rounded-bottom')
        }
        
        root_attach.classList.add('text-danger');
        root_attach_icon.src='../dist/img/chevron-down.svg'
    }

}

//生成列表
function generateLists(rot,vi,to,type){
    var root=document.getElementById(rot);
    var uls_head='<div class="list-group border-0 rounded-0 ps-3 pb-2 pe-2">'
    var uls_end='</div>'
    var lis=''
    
    switch(type){
        case 0:
            lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start list-group-item-action grayext" style="font-size: 14px;" onclick="openDialog(0,0,0)">'+'拼音显示格式'+'<div class="pe-2 ps-auto text-primary"><span id="hk_visibles">'+vi+'<span></div></li>'
            lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start list-group-item-action grayext" style="font-size: 14px;" onclick="openDialog(2,0,1)">'+'声调显示格式'+'<div class="pe-2 ps-auto text-primary"><span id="hktone"><span>'+to+'</div></li>'
            break;
        case 1:
            lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start list-group-item-action grayext" style="font-size: 14px;" onclick="openDialog(1,1,0)">'+'拼音显示格式'+'<div class="pe-2 ps-auto text-primary"><span id="cmn_visibles">'+vi+'<span></div></li>'
            lis+='<li class="list-group-item border-0 d-flex justify-content-between align-content-start list-group-item-action grayext" style="font-size: 14px;" onclick="openDialog(2,1,1)">'+'声调显示格式'+'<div class="pe-2 ps-auto text-primary"><span id="cmntone">'+to+'</div></li>'
            break;
    }

    root.innerHTML= uls_head+lis+uls_end;

}



//更新单个设置条目的值
function updateSingleValue(id,str){
    var sp=document.getElementById(id);
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

//保存spValue，並传递给APP更新数据
function updateStoredValues(){

}

//调用APP对话框
//key索引值，map映射索引，tag前置标签索引，abbr缩略标签索引
function openDialog(map,tag,abbr){
    var tags=['hk','cmn','sw','kx','hd','ltc']
    var mappingvalue=['hk_visibles','cmn_visibles','tones','typographys','bgcolors','directions']
    var abbreviations=['_visibles','tone','drt','ft','lt','lh','bg']

    app.showaloh(0,mappingvalue[map],tags[tag]+abbreviations[abbr]);
    
}


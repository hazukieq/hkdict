window.onload=onchag(1);


function oninfo(){
    var ser=document.getElementById('inputmsg').value;
    app.info(ser);
}
function onchag(num){
    var listg=document.getElementById('listg');
    var listf=document.getElementById('list-first');


    let ele;
    var fir=listf.children;
    for(var d=0;d<fir.length;d++){
        fir[d].setAttribute('style','background-color:gray');
    }

    fir[(num-1)].setAttribute('style','background-color:royalblue;color:white');
    while((ele=listg.firstChild)){
        ele.remove();
    }
    onese=['天象地理','矿物及无生命自然物','方位时间','动物','植物','身体心理','亲属','人的称谓','居住建筑交通','服饰','生活用具武器','生产工具','经济文化娱乐','行政区域民族','宗教风俗','疾病医药','食品饲料','抽象及其他','新名词','增加名词'];
    secondse=['自然现象及一般事物的活动变化','动植物活动及生长变化','判断、存在、趋向、能愿动词','心理活动','疾病医疗','风俗迷信','五官动作','日常生活一般行为动作','手的动作(不用工具)','手的动作(用工具)','脚的动作','手工业劳动','农牧业劳动','炊事劳动','经济活动','抽象行为及其它','新借动词'];
    thirse='代词';
    fourse='连词';
    fifse='助词';
    sixse='形容词';
    sevense='数词';
    eightse='量词';

    switch(num){
        case 1:
            for(var h=0;h<20;h++){
            var a=document.createElement('a');
            a.setAttribute('class','list-group-item border-0  acover');
            a.innerHTML=onese[h];
            a.setAttribute('href','http://43.142.122.229/words/lwordse.html?secaid='+(h+1));
            listg.appendChild(a);
            }
            break;
        case 2:
        for(var h=0;h<17;h++){
            var a=document.createElement('a');
            a.setAttribute('class','list-group-item border-0 acover');
            a.innerHTML=secondse[h];
            a.setAttribute('href','http://43.142.122.229/words/lwordse.html?secaid='+(20+h+1));
            listg.appendChild(a);
            }
            break;
        case 3:
        var a=document.createElement('a');
            a.setAttribute('class','list-group-item border-0  acover');
            a.innerHTML=thirse;
            a.setAttribute('href','http://43.142.122.229/words/lwordse.html?secaid=38');
            listg.appendChild(a);
            break;
        case 4:
        var a=document.createElement('a');
            a.setAttribute('class','list-group-item border-0  acover');
            a.innerHTML=fourse;
            a.setAttribute('href','http://43.142.122.229/words/lwordse.html?secaid=39');
            listg.appendChild(a);
            break;
        case 5:
        var a=document.createElement('a');
            a.setAttribute('class','list-group-item border-0  acover');
            a.innerHTML=fifse;
            a.setAttribute('href','http://43.142.122.229/words/lwordse.html?secaid=40');
            listg.appendChild(a);
            break;
        case 6:
        var a=document.createElement('a');
            a.setAttribute('class','list-group-item border-0  acover');
            a.innerHTML=sixse;
            a.setAttribute('href','http://43.142.122.229/words/lwordse.html?secaid=41');
            listg.appendChild(a);
            break;
        case 7:
        var a=document.createElement('a');
            a.setAttribute('class','list-group-item border-0  acover');
            a.innerHTML=sevense;
            a.setAttribute('href','http://43.142.122.229/words/lwordse.html?secaid=42');
            listg.appendChild(a);
            break;
        case 8:
        var a=document.createElement('a');
            a.setAttribute('class','list-group-item border-0  acover');
            a.innerHTML=eightse;
            a.setAttribute('href','http://43.142.122.229/words/lwordse.html?secaid=43');
            listg.appendChild(a);
            break;
    }
}
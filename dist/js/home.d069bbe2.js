(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["home"],{
    "02de":function(e,t,n){
        "use strict";
        n.d(t,"a",(function(){
            return i
        }));
        var r=n("7a23");

        function i(e){
            var t=Object(r["M"])(e);
            if(!t) return !1;
            var n=window.getComputedStyle(t),i="none"===n.display,a=null===t.offsetParent&&"fixed"!==n.position;
            return i||a
        }
    },"09fe":function(e,t,n){
    },"159b":function(e,t,n){
        var r=n("da84"),i=n("fdbc"),a=n("17c2"),o=n("9112");
        for(var c in i){
            var u=r[c],d=u&&u.prototype;
            if(d&&d.forEach!==a) try{
                o(d,"forEach",a)
            }
            catch(s){
                d.forEach=a
            }
        }
    },"17c2":function(e,t,n){
        "use strict";
        var r=n("b727").forEach,i=n("a640"),a=n("ae40"),o=i("forEach"),c=a("forEach");
        e.exports=o&&c?[].forEach:function(e){
            return r(this,e,arguments.length>1?arguments[1]:void 0)
        }
    },"28ca":function(e,t,n){
        "use strict";
        n("30a1")
    },2994:function(e,t,n){
        "use strict";
        n("68ef"), n("e3b3"), n("c0c2")
    },"2bdd":function(e,t,n){
        "use strict";
        var r=n("3835"),i=n("7a23"),a=n("d282"),o=n("02de"),c=n("52709"),u=n("2744"),d=n("fa00"),s=n("4eda"),
            l=n("543e"),f=Object(a["a"])("list"),v=Object(r["a"])(f,3),b=v[0],j=v[1],h=v[2];
        t["a"]=b({
            props:{
                error:Boolean,
                loading:Boolean,
                finished:Boolean,
                errorText:String,
                loadingText:String,
                finishedText:String,
                offset:{type:[Number,String],default:300},
                direction:{type:String,default:"down"},
                immediateCheck:{type:Boolean,default:!0}
            },emits:["load","update:error","update:loading"],setup:function(e,t){
                var n=t.emit,r=t.slots,a=Object(i["E"])(!1),f=Object(i["E"])(),v=Object(i["E"])(),b=Object(c["a"])(f),
                    g=function(){
                        Object(i["q"])((function(){
                            if(!(a.value||e.finished||e.error)){
                                var t=e.offset,r=e.direction,i=Object(u["a"])(b);
                                if(!i.height||Object(o["a"])(f)) return !1;
                                var c=!1,d=Object(u["a"])(v);
                                c="up"===r?i.top-d.top<=t:d.bottom-i.bottom<=t, c&&(a.value= !0, n("update:loading",!0), n("load"))
                            }
                        }))
                    },p=function(){
                        if(e.finished){
                            var t=r.finished?r.finished():e.finishedText;
                            if(t) return Object(i["j"])("div",{class:j("finished-text")},[t])
                        }
                    },O=function(){
                        n("update:error",!1), g()
                    },m=function(){
                        if(e.error){
                            var t=r.error?r.error():e.errorText;
                            if(t) return Object(i["j"])("div",{class:j("error-text"),onClick:O},[t])
                        }
                    },w=function(){
                        if(a.value&& !e.finished) return Object(i["j"])("div",{class:j("loading")},[r.loading?r.loading():Object(i["j"])(l["a"],{size:16},{
                            default:function(){
                                return [e.loadingText||h("loading")]
                            }
                        })])
                    };
                return Object(i["P"])([function(){
                    return e.loading
                },function(){
                    return e.finished
                }],g), Object(i["y"])((function(){
                    a.value=e.loading
                })), Object(i["w"])((function(){
                    e.immediateCheck&&g()
                })), Object(s["a"])({check:g}), Object(d["a"])("scroll",g,{target:b}), function(){
                    var t,n=null===(t=r.default)|| void 0===t?void 0:t.call(r),
                        o=Object(i["j"])("div",{ref:v,class:j("placeholder")},null);
                    return Object(i["j"])("div",{
                        ref:f,
                        role:"feed",
                        class:j(),
                        "aria-busy":a.value
                    },["down"===e.direction?n:o,w(),p(),m(),"up"===e.direction?n:o])
                }
            }
        })
    },"30a1":function(e,t,n){
    },4056:function(e,t,n){
        "use strict";
        n("68ef"), n("cb51"), n("3743"), n("09fe")
    },4160:function(e,t,n){
        "use strict";
        var r=n("23e7"),i=n("17c2");
        r({target:"Array",proto:!0,forced:[].forEach!=i},{forEach:i})
    },"44bf":function(e,t,n){
        "use strict";
        var r=n("3835"),i=n("7a23"),a=n("d282"),o=n("db17"),c=n("ea8e"),u=n("ad06"),d=Object(a["a"])("image"),
            s=Object(r["a"])(d,2),l=s[0],f=s[1];
        t["a"]=l({
            props:{
                src:String,
                alt:String,
                fit:String,
                round:Boolean,
                width:[Number,String],
                height:[Number,String],
                radius:[Number,String],
                lazyLoad:Boolean,
                iconPrefix:String,
                showError:{type:Boolean,default:!0},
                showLoading:{type:Boolean,default:!0},
                errorIcon:{type:String,default:"photo-fail"},
                loadingIcon:{type:String,default:"photo"}
            },emits:["load","error"],setup:function(e,t){
                var n=t.emit,r=t.slots,a=Object(i["E"])(!1),d=Object(i["E"])(!0),s=Object(i["E"])(),
                    l=Object(i["e"])((function(){
                        var t={};
                        return Object(o["c"])(e.width)&&(t.width=Object(c["a"])(e.width)), Object(o["c"])(e.height)&&(t.height=Object(c["a"])(e.height)), Object(o["c"])(e.radius)&&(t.overflow="hidden", t.borderRadius=Object(c["a"])(e.radius)), t
                    }));
                Object(i["P"])((function(){
                    return e.src
                }),(function(){
                    a.value= !1, d.value= !0
                }));
                var v=function(e){
                    d.value= !1, n("load",e)
                },b=function(e){
                    a.value= !0, d.value= !1, n("error",e)
                },j=function(){
                    return r.loading?r.loading():Object(i["j"])(u["a"],{
                        name:e.loadingIcon,
                        class:f("loading-icon"),
                        classPrefix:e.iconPrefix
                    },null)
                },h=function(){
                    return r.error?r.error():Object(i["j"])(u["a"],{
                        name:e.errorIcon,
                        class:f("error-icon"),
                        classPrefix:e.iconPrefix
                    },null)
                },g=function(){
                    return d.value&&e.showLoading&&o["b"]?Object(i["j"])("div",{class:f("loading")},[j()]):a.value&&e.showError?Object(i["j"])("div",{class:f("error")},[h()]):void 0
                },p=function(){
                    if(!a.value&&e.src){
                        var t={alt:e.alt,class:f("img"),style:{objectFit:e.fit}};
                        return e.lazyLoad?Object(i["R"])(Object(i["j"])("img",Object(i["p"])({ref:s},t),null),[[Object(i["H"])("lazy"),e.src]]):Object(i["j"])("img",Object(i["p"])({
                            src:e.src,
                            onLoad:v,
                            onError:b
                        },t),null)
                    }
                };
                return function(){
                    var t;
                    return Object(i["j"])("div",{
                        class:f({round:e.round}),
                        style:l.value
                    },[p(),g(),null===(t=r.default)|| void 0===t?void 0:t.call(r)])
                }
            }
        })
    },4707:function(e,t,n){
    },"4fad":function(e,t,n){
        var r=n("23e7"),i=n("6f53").entries;
        r({target:"Object",stat:!0},{
            entries:function(e){
                return i(e)
            }
        })
    },52709:function(e,t,n){
        "use strict";
        n.d(t,"a",(function(){
            return c
        }));
        var r=n("7a23"),i=/scroll|auto/i;

        function a(e){
            var t=1;
            return "HTML"!==e.tagName&&e.nodeType===t
        }

        function o(e,t){
            void 0===t&&(t=window);
            var n=e;
            while(n&&n!==t&&a(n)){
                var r=window.getComputedStyle(n).overflowY;
                if(i.test(r)){
                    if("BODY"!==n.tagName) return n;
                    var o=window.getComputedStyle(n.parentNode).overflowY;
                    if(i.test(o)) return n
                }
                n=n.parentNode
            }
            return t
        }

        function c(e){
            var t=Object(r["E"])();
            return Object(r["w"])((function(){
                e.value&&(t.value=o(e.value))
            })), t
        }
    },"6ab3":function(e,t,n){
    },"6f53":function(e,t,n){
        var r=n("83ab"),i=n("df75"),a=n("fc6a"),o=n("d1e7").f,c=function(e){
            return function(t){
                var n,c=a(t),u=i(c),d=u.length,s=0,l=[];
                while(d>s) n=u[s++], r&& !o.call(c,n)||l.push(e?[n,c[n]]:c[n]);
                return l
            }
        };
        e.exports={entries:c(!0),values:c(!1)}
    },"7b0bb":function(e,t,n){
        "use strict";
        n("4707")
    },"99af":function(e,t,n){
        "use strict";
        var r=n("23e7"),i=n("d039"),a=n("e8b5"),o=n("861d"),c=n("7b0b"),u=n("50c4"),d=n("8418"),s=n("65f0"),l=n("1dde"),
            f=n("b622"),v=n("2d00"),b=f("isConcatSpreadable"),j=9007199254740991,h="Maximum allowed index exceeded",
            g=v>=51|| !i((function(){
                var e=[];
                return e[b]= !1, e.concat()[0]!==e
            })),p=l("concat"),O=function(e){
                if(!o(e)) return !1;
                var t=e[b];
                return void 0!==t?!!t:a(e)
            },m=!g|| !p;
        r({target:"Array",proto:!0,forced:m},{
            concat:function(e){
                var t,n,r,i,a,o=c(this),l=s(o,0),f=0;
                for(t= -1, r=arguments.length; t<r; t++) if(a=-1===t?o:arguments[t], O(a)){
                    if(i=u(a.length), f+i>j) throw TypeError(h);
                    for(n=0; n<i; n++, f++) n in a&&d(l,f,a[n])
                }else{
                    if(f>=j) throw TypeError(h);
                    d(l,f++,a)
                }
                return l.length=f, l
            }
        })
    },a8c1:function(e,t,n){
        "use strict";
        n.d(t,"b",(function(){
            return o
        })), n.d(t,"g",(function(){
            return c
        })), n.d(t,"f",(function(){
            return d
        })), n.d(t,"a",(function(){
            return s
        })), n.d(t,"c",(function(){
            return l
        })), n.d(t,"d",(function(){
            return f
        })), n.d(t,"e",(function(){
            return b
        }));
        var r=n("db17");

        function i(){
            return !!r["b"]&&/ios|iphone|ipad|ipod/.test(navigator.userAgent.toLowerCase())
        }

        function a(e){
            return e===window
        }

        function o(e){
            var t="scrollTop" in e?e.scrollTop:e.pageYOffset;
            return Math.max(t,0)
        }

        function c(e,t){
            "scrollTop" in e?e.scrollTop=t:e.scrollTo(e.scrollX,t)
        }

        function u(){
            return window.pageYOffset||document.documentElement.scrollTop||document.body.scrollTop||0
        }

        function d(e){
            c(window,e), c(document.body,e)
        }

        function s(e,t){
            if(a(e)) return 0;
            var n=t?o(t):u();
            return e.getBoundingClientRect().top+n
        }

        function l(e){
            return a(e)?e.innerHeight:e.getBoundingClientRect().height
        }

        function f(e){
            return a(e)?0:e.getBoundingClientRect().top
        }

        var v=i();

        function b(){
            v&&d(u())
        }
    },bb51:function(e,t,n){
        "use strict";
        n.r(t);
        var r=n("7a23"),i=Object(r["T"])("data-v-4213e3d6");
        Object(r["C"])("data-v-4213e3d6");
        var a={class:"video-list"};
        Object(r["A"])();
        var o=i((function(e,t,n,o,c,u){
                var d=Object(r["G"])("video-card"),s=Object(r["G"])("van-list"),l=Object(r["G"])("van-pull-refresh");
                return Object(r["z"])(), Object(r["g"])(l,{
                    modelValue:e.refreshing,
                    "onUpdate:modelValue":t[2]||(t[2]=function(t){
                        return e.refreshing=t
                    }),
                    onRefresh:e.handleRefresh
                },{
                    default:i((function(){
                        return [Object(r["j"])(s,{
                            class:"list-container",
                            "immediate-check":!1,
                            modelValue:e.loading,
                            "onUpdate:modelValue":t[1]||(t[1]=function(t){
                                return e.loading=t
                            }),
                            finished:e.finished,
                            "finished-text":"没有更多了",
                            onLoad:e.handleLoad
                        },{
                            default:i((function(){
                                return [Object(r["j"])("div",a,[(Object(r["z"])(!0), Object(r["g"])(r["a"],null,Object(r["F"])(e.videoList,(function(t){
                                    return Object(r["z"])(), Object(r["g"])(d,{
                                        key:t.id,video:t,onClick:function(n){
                                            return e.handleVideoCardClick(t.id)
                                        }
                                    },null,8,["video","onClick"])
                                })),128))])]
                            })),_:1
                        },8,["modelValue","finished","onLoad"])]
                    })),_:1
                },8,["modelValue","onRefresh"])
            })),c=(n("99af"), n("d3b7"), n("25f0"), n("96cf"), n("1da1")),u=(n("2994"), n("2bdd")),
            d=(n("68ef"), n("e3b3"), n("6ab3"), n("3835")),s=n("d282"),l=n("a8c1"),f=n("1325"),v=n("52709"),b=n("cdd8"),
            j=n("543e"),h=Object(s["a"])("pull-refresh"),g=Object(d["a"])(h,3),p=g[0],O=g[1],m=g[2],w=50,
            T=["pulling","loosing","success"],x=p({
                props:{
                    disabled:Boolean,
                    successText:String,
                    pullingText:String,
                    loosingText:String,
                    loadingText:String,
                    modelValue:{type:Boolean,required:!0},
                    successDuration:{type:[Number,String],default:500},
                    animationDuration:{type:[Number,String],default:300},
                    headHeight:{type:[Number,String],default:w}
                },emits:["refresh","update:modelValue"],setup:function(e,t){
                    var n,i=t.emit,a=t.slots,o=Object(r["E"])(),c=Object(v["a"])(o),
                        u=Object(r["D"])({status:"normal",distance:0,duration:0}),d=Object(b["a"])(),s=function(){
                            if(e.headHeight!==w) return {height:"".concat(e.headHeight,"px")}
                        },h=function(){
                            return "loading"!==u.status&&"success"!==u.status&& !e.disabled
                        },g=function(t){
                            var n=+e.headHeight;
                            return t>n&&(t=t<2*n?n+(t-n)/2:1.5*n+(t-2*n)/4), Math.round(t)
                        },p=function(t,n){
                            u.distance=t, n?u.status="loading":0===t?u.status="normal":t<e.headHeight?u.status="pulling":u.status="loosing"
                        },x=function(){
                            var t=u.status;
                            return "normal"===t?"":e["".concat(t,"Text")]||m(t)
                        },y=function(){
                            var e=u.status,t=u.distance;
                            if(a[e]) return a[e]({distance:t});
                            var n=[];
                            return -1!==T.indexOf(e)&&n.push(Object(r["j"])("div",{class:O("text")},[x()])), "loading"===e&&n.push(Object(r["j"])(j["a"],{size:"16"},{
                                default:function(){
                                    return [x()]
                                }
                            })), n
                        },E=function(){
                            u.status="success", setTimeout((function(){
                                p(0)
                            }),+e.successDuration)
                        },k=function(e){
                            n=0===Object(l["b"])(c.value), n&&(u.duration=0, d.start(e))
                        },S=function(e){
                            h()&&k(e)
                        },C=function(e){
                            if(h()){
                                n||k(e);
                                var t=d.deltaY;
                                d.move(e), n&&t.value>=0&&d.isVertical()&&(Object(f["a"])(e), p(g(t.value)))
                            }
                        },R=function(){
                            n&&d.deltaY.value&&h()&&(u.duration= +e.animationDuration, "loosing"===u.status?(p(+e.headHeight,!0), i("update:modelValue",!0), Object(r["q"])((function(){
                                i("refresh")
                            }))):p(0))
                        };
                    return Object(r["P"])((function(){
                        return e.modelValue
                    }),(function(t){
                        u.duration= +e.animationDuration, t?p(+e.headHeight,!0):a.success||e.successText?E():p(0,!1)
                    })), function(){
                        var e,t={
                            transitionDuration:"".concat(u.duration,"ms"),
                            transform:u.distance?"translate3d(0,".concat(u.distance,"px, 0)"):""
                        };
                        return Object(r["j"])("div",{ref:o,class:O()},[Object(r["j"])("div",{
                            class:O("track"),
                            style:t,
                            onTouchstart:S,
                            onTouchmove:C,
                            onTouchend:R,
                            onTouchcancel:R
                        },[Object(r["j"])("div",{
                            class:O("head"),
                            style:s()
                        },[y()]),null===(e=a.default)|| void 0===e?void 0:e.call(a)])])
                    }
                }
            }),y=Object(r["T"])("data-v-02467010");
        Object(r["C"])("data-v-02467010");
        var E={class:"video-intro"},k={class:"video-title"},S={class:"video-info"},C={class:"video-tag"},
            R={class:"video-likes"};
        Object(r["A"])();
        var L=y((function(e,t,n,i,a,o){
            var c=Object(r["G"])("van-image"),u=Object(r["G"])("van-icon");
            return Object(r["z"])(), Object(r["g"])("div",{
                class:"card-wrapper",onClick:t[1]||(t[1]=function(){
                    return e.handleClick.apply(e,arguments)
                })
            },[Object(r["j"])(c,{
                class:"video-cover",
                fit:"contain",
                src:e.video.cover
            },null,8,["src"]),Object(r["j"])("div",E,[Object(r["j"])("div",k,Object(r["K"])(e.video.title),1),Object(r["j"])("div",S,[Object(r["j"])("div",C,Object(r["K"])(e.video.category),1),Object(r["j"])("div",R,[Object(r["j"])(u,{
                class:"likes-icon",
                name:"good-job-o"
            }),Object(r["j"])("div",null,Object(r["K"])(e.video.likes),1)])])])])
        })),V=(n("c3a6"), n("ad06")),P=(n("4056"), n("44bf")),B=Object(r["k"])({
            components:{"van-image":P["a"],"van-icon":V["a"]},
            name:"VideoCard",
            props:{video:{type:Object,required:!0}},
            methods:{
                handleClick:function(e){
                    this.$emit("click",e)
                }
            }
        });
        n("28ca");
        B.render=L, B.__scopeId="data-v-02467010";
        var z=B,D=n("c5e7"),H=Object(r["k"])({
            components:{VideoCard:z,"van-pull-refresh":x,"van-list":u["a"]},name:"Home",data:function(){
                return {videoList:[],paging:{page:1,per_page:10},refreshing:!1,loading:!1,finished:!1,scrollTop:0}
            },created:function(){
                this.getRecommends()
            },activated:function(){
                var e=this;
                setTimeout((function(){
                    document.body.scrollTop=e.scrollTop
                }),0)
            },beforeRouteLeave:function(){
                this.scrollTop=document.body.scrollTop
            },methods:{
                getRecommends:function(){
                    var e=arguments,t=this;
                    return Object(c["a"])(regeneratorRuntime.mark((function n(){
                        var r,i,a;
                        return regeneratorRuntime.wrap((function(n){
                            while(1) switch(n.prev=n.next){
                                case 0:
                                    return r=e.length>0&& void 0!==e[0]&&e[0], n.prev=1, n.next=4, Object(D["f"])(t.paging);
                                case 4:
                                    i=n.sent, n.next=11;
                                    break;
                                case 7:
                                    return n.prev=7, n.t0=n["catch"](1), n.abrupt("return");
                                case 11:
                                    a=i.data, t.videoList=r?t.videoList.concat(a):a, t.finished=a.length<t.paging.per_page;
                                case 14:
                                case"end":
                                    return n.stop()
                            }
                        }),n,null,[[1,7]])
                    })))()
                },handleRefresh:function(){
                    var e=this;
                    return Object(c["a"])(regeneratorRuntime.mark((function t(){
                        return regeneratorRuntime.wrap((function(t){
                            while(1) switch(t.prev=t.next){
                                case 0:
                                    return e.paging.page=1, e.finished= !1, t.next=4, e.getRecommends();
                                case 4:
                                    return t.next=6, e.$nextTick();
                                case 6:
                                    e.refreshing= !1;
                                case 7:
                                case"end":
                                    return t.stop()
                            }
                        }),t)
                    })))()
                },handleLoad:function(){
                    var e=this;
                    return Object(c["a"])(regeneratorRuntime.mark((function t(){
                        return regeneratorRuntime.wrap((function(t){
                            while(1) switch(t.prev=t.next){
                                case 0:
                                    if(!0!==e.loading){
                                        t.next=2;
                                        break
                                    }
                                    return t.abrupt("return");
                                case 2:
                                    return e.loading= !0, e.paging.page++, t.next=6, e.getRecommends(!0);
                                case 6:
                                    return t.next=8, e.$nextTick();
                                case 8:
                                    e.loading= !1;
                                case 9:
                                case"end":
                                    return t.stop()
                            }
                        }),t)
                    })))()
                },handleVideoCardClick:function(e){
                    this.$router.push({name:"Video",params:{video_id:e.toString()}})
                }
            }
        });
        n("7b0bb");
        H.render=o, H.__scopeId="data-v-4213e3d6";
        t["default"]=H
    },c0c2:function(e,t,n){
    },c5e7:function(e,t,n){
        "use strict";
        n.d(t,"f",(function(){
            return a
        })), n.d(t,"j",(function(){
            return o
        })), n.d(t,"m",(function(){
            return c
        })), n.d(t,"n",(function(){
            return u
        })), n.d(t,"g",(function(){
            return d
        })), n.d(t,"i",(function(){
            return s
        })), n.d(t,"k",(function(){
            return l
        })), n.d(t,"l",(function(){
            return f
        })), n.d(t,"h",(function(){
            return v
        })), n.d(t,"c",(function(){
            return b
        })), n.d(t,"e",(function(){
            return j
        })), n.d(t,"b",(function(){
            return h
        })), n.d(t,"d",(function(){
            return g
        })), n.d(t,"a",(function(){
            return p
        }));
        n("4160"), n("4fad"), n("159b");
        var r=n("3835"),i=n("f977");

        function a(e){
            return Object(i["a"])({url:"/recommends",params:e,withCredentials:true})
        }

        function o(e){
            return Object(i["a"])({url:"/videos",params:e,withCredentials:true})
        }

        function c(e){
            return Object(i["a"])({url:"/search/videos",params:e,withCredentials:true})
        }

        function u(e,t){
            var n=new FormData;
            return n.append("file",e), Object.entries(t).forEach((function(e){
                var t=Object(r["a"])(e,2),i=t[0],a=t[1];
                n.append(i,a)
            })), Object(i["a"])({
                url:"/user/videos",
                method:"POST",
                headers:{"Content-Type":"multipart/form-data"},
                data:n,
                timeout:0
            })
        }

        function d(e){
            return Object(i["a"])({url:"/videos/".concat(e)})
        }

        function s(e,t){
            return Object(i["a"])({url:"/videos/".concat(e,"/comments"),params:t})
        }

        function l(e,t){
            return Object(i["a"])({url:"/videos/".concat(e,"/comments"),method:"POST",data:t})
        }

        function f(e){
            return Object(i["a"])({url:"/user/played/".concat(e),method:"PUT"})
        }

        function v(e){
            return Object(i["a"])({url:"user/liked/".concat(e),method:"PUT"})
        }

        function b(e){
            return Object(i["a"])({url:"user/liked/".concat(e),method:"DELETE"})
        }

        function j(e){
            return Object(i["a"])({url:"user/disliked/".concat(e),method:"PUT"})
        }

        function h(e){
            return Object(i["a"])({url:"user/disliked/".concat(e),method:"DELETE"})
        }

        function g(e){
            return Object(i["a"])({url:"user/favorites/".concat(e),method:"PUT"})
        }

        function p(e){
            return Object(i["a"])({url:"user/favorites/".concat(e),method:"DELETE"})
        }
    },cdd8:function(e,t,n){
        "use strict";
        n.d(t,"a",(function(){
            return o
        }));
        var r=n("7a23"),i=10;

        function a(e,t){
            return e>t&&e>i?"horizontal":t>e&&t>i?"vertical":""
        }

        function o(){
            var e=Object(r["E"])(0),t=Object(r["E"])(0),n=Object(r["E"])(0),i=Object(r["E"])(0),o=Object(r["E"])(0),
                c=Object(r["E"])(0),u=Object(r["E"])(""),d=function(){
                    return "vertical"===u.value
                },s=function(){
                    return "horizontal"===u.value
                },l=function(){
                    n.value=0, i.value=0, o.value=0, c.value=0, u.value=""
                },f=function(n){
                    l(), e.value=n.touches[0].clientX, t.value=n.touches[0].clientY
                },v=function(r){
                    var d=r.touches[0];
                    n.value=d.clientX-e.value, i.value=d.clientY-t.value, o.value=Math.abs(n.value), c.value=Math.abs(i.value), u.value||(u.value=a(o.value,c.value))
                };
            return {
                move:v,
                start:f,
                reset:l,
                startX:e,
                startY:t,
                deltaX:n,
                deltaY:i,
                offsetX:o,
                offsetY:c,
                direction:u,
                isVertical:d,
                isHorizontal:s
            }
        }
    }
}]);

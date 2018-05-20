<div class="page-sidebar" id="sidebar">
    <!-- Page Sidebar Header-->
    <div class="sidebar-header-wrapper">
        <input type="text" class="searchinput"/>
        <i class="searchicon fa fa-search"></i>
        <div class="searchhelper">搜索</div>
    </div>
<ul class="nav sidebar-menu">
    <!--Dashboard-->
        <@shiro.hasPermission name="userIndex:tag">
        <li <#if pageId==1>class="active" </#if>>
            <a href="/oa/user/index.html">
                <i class="menu-icon glyphicon glyphicon-home"></i>
                <span class="menu-text"> 控制台 </span>
            </a>
        </li>
        </@shiro.hasPermission>
    <!--UI 个人工资-->
        <@shiro.hasPermission name="personnelSalary:tag">
        <li <#if pageId==37>class="active" </#if>>
            <a href="/oa/personnelSalary/list/user.html">
                <i class="menu-icon glyphicon glyphicon-flash"></i>
                <span class="menu-text"> 个人工资 </span>
            </a>
        </li>
        </@shiro.hasPermission>
    <!--UI 员工预支-->
        <@shiro.hasPermission name="personnelSalaryAdvanc:tag">
        <li <#if pageId==2|| pageId==3>class="open" </#if>>
            <a href="#" class="menu-dropdown">
                <i class="menu-icon fa fa-desktop"></i>
                <span class="menu-text">员工预支</span>
                <i class="menu-expand"></i>
            </a>
            <ul class="submenu">
                <li <#if pageId==2>class="active" </#if>>
                    <a href="/oa/personnelSalaryAdvance/list/user.html">
                        <span class="menu-text">记录查询</span>
                    </a>
                </li>
                <li <#if pageId==3>class="active" </#if>>
                    <a href="/oa/personnelSalaryAdvance/index.html" class="menu-dropdown">
                        <span class="menu-text">预支申请</span>
                    </a>
                </li>
            </ul>
        </li>
        </@shiro.hasPermission>
    <!--主播预支-->
         <@shiro.hasPermission name="anchorSalaryAdvance:tag">
        <li <#if pageId==4|| pageId==5>class="open" </#if>>
            <a href="#" class="menu-dropdown">
                <i class="menu-icon fa fa-table"></i>
                <span class="menu-text"> 主播预支 </span>
                <i class="menu-expand"></i>
            </a>
            <ul class="submenu">
                <li <#if pageId==4>class="active" </#if>>
                    <a href="/oa/anchorSalaryAdvance/list/user.html">
                        <span class="menu-text">记录查询</span>
                    </a>
                </li>
                <li <#if pageId==5>class="active" </#if>>
                    <a href="/oa/anchorSalaryAdvance/index.html">
                        <span class="menu-text">预支申请</span>
                    </a>
                </li>
            </ul>
        </li>
         </@shiro.hasPermission>
        <!--主播提现-->
             <@shiro.hasPermission name="spending:tag">
         <li <#if pageId==7|| pageId==8>class="open" </#if>>
             <a href="#" class="menu-dropdown">
                 <i class="menu-icon fa fa-building-o"></i>
                 <span class="menu-text"> 日常开支 </span>

                 <i class="menu-expand"></i>
             </a>

             <ul class="submenu">
                 <li <#if pageId==7>class="active" </#if>>
                     <a href="/oa/spending/list/user.html">
                         <span class="menu-text">申请记录</span>
                     </a>
                 </li>

                 <li <#if pageId==8>class="active" </#if>>
                     <a href="/oa/spending/index.html">
                         <span class="menu-text">开支申请</span>
                     </a>
                 </li>

             </ul>
         </li>
             </@shiro.hasPermission>
        <!--物品借记-->
             <@shiro.hasPermission name="itemDebit:tag">

        <li  <#if pageId==9|| pageId==10>class="open" </#if>>
            <a href="#" class="menu-dropdown">
                <i class="menu-icon fa fa-envelope-o"></i>
                <span class="menu-text"> 物品借记 </span>

                <i class="menu-expand"></i>
            </a>

            <ul class="submenu">
                <li <#if pageId==9>class="active" </#if>>
                    <a href="/oa/itemDebit/list/user.html">
                        <span class="menu-text">借记记录</span>
                    </a>
                </li>

                <li <#if pageId==10>class="active" </#if>>
                    <a href="/oa/itemDebit/index.html">
                        <span class="menu-text">借记添加</span>
                    </a>
                </li>
            </ul>
        </li>
             </@shiro.hasPermission>

        <!--M人员管理-->
             <@shiro.hasPermission name="staff:tag">
        <li
        <#if pageId==11|| pageId==12|| pageId==13|| pageId==14|| pageId==15|| pageId==16>class="active open" </#if> >
            <a href="#" class="menu-dropdown">
                <i class="menu-icon fa fa-user"></i>
                <span class="menu-text">人员管理</span>

                <i class="menu-expand"></i>
            </a>

            <ul class="submenu">



         <@shiro.hasPermission name="personnel:tag">
            <li <#if pageId==12|| pageId==11>class=" open" </#if>>
                <a href="#" class="menu-dropdown">
                                    <span class="menu-text">
                                        工作人员
                                    </span>
                    <i class="menu-expand"></i>
                </a>

                <ul class="submenu">
                    <li <#if pageId==11>class="active" </#if>>
                        <a href="/oa/personnel/index.html">
                            <i class="menu-icon fa fa-rocket"></i>
                            <span class="menu-text">添加人员</span>
                        </a>
                    </li>
                    <li <#if pageId==12>class="active" </#if>>
                        <a href="/oa/personnel/list.html">
                            <i class="menu-icon glyphicon glyphicon-stats"></i>
                            <span class="menu-text">查询人员</span>
                        </a>
                    </li>
                </ul>
            </li>
         </@shiro.hasPermission>
         <@shiro.hasPermission name="anchorUser:tag">
            <li <#if pageId==13|| pageId==14>class=" open" </#if>>
                <a href="#" class="menu-dropdown">
                                    <span class="menu-text">
                                        主播用户
                                    </span>
                    <i class="menu-expand"></i>
                </a>

                <ul class="submenu">
                    <li <#if pageId==13>class="active" </#if>>
                        <a href="/oa/anchor/user/index.html">
                            <i class="menu-icon fa fa-rocket"></i>
                            <span class="menu-text">添加用户</span>
                        </a>
                    </li>
                    <li <#if pageId==14>class="active" </#if>>
                        <a href="/oa/anchor/user/list.html">
                            <i class="menu-icon glyphicon glyphicon-stats"></i>
                            <span class="menu-text">查询用户</span>
                        </a>
                    </li>
                </ul>
            </li>
         </@shiro.hasPermission>
         <@shiro.hasPermission name="anchor:tag">
            <li <#if pageId==15|| pageId==16>class=" open" </#if>>
                <a href="#" class="menu-dropdown">
                                    <span class="menu-text">
                                        平台主播
                                    </span>
                    <i class="menu-expand"></i>
                </a>

                <ul class="submenu">
                    <li <#if pageId==15>class="active" </#if>>
                        <a href="/oa/anchor/index.html">
                            <i class="menu-icon fa fa-rocket"></i>
                            <span class="menu-text">添加主播</span>
                        </a>
                    </li>
                    <li <#if pageId==16>class="active" </#if>>
                        <a href="/oa/anchor/list.html">
                            <i class="menu-icon glyphicon glyphicon-stats"></i>
                            <span class="menu-text">查询主播</span>
                        </a>
                    </li>
                </ul>
            </li>
         </@shiro.hasPermission>
            </ul>
        </li>
             </@shiro.hasPermission>
        <!--部门管理-->
             <@shiro.hasPermission name="dept:tag">
        <li <#if pageId==17|| pageId==18>class="active open" </#if>>
            <a href="#" class="menu-dropdown">
                <i class="menu-icon glyphicon glyphicon-paperclip"></i>
                <span class="menu-text"> 部门管理 </span>

                <i class="menu-expand"></i>
            </a>
            <ul class="submenu">
                <li <#if pageId==17>class="active" </#if>>
                    <a href="/oa/dept/index.html">
                        <span class="menu-text">添加部门</span>
                    </a>
                </li>
                <li <#if pageId==18>class="active" </#if>>
                    <a href="/oa/dept/list.html">
                        <span class="menu-text">查询部门</span>
                    </a>
                </li>

            </ul>
        </li>
             </@shiro.hasPermission>
        <!--平台管理-->
             <@shiro.hasPermission name="platform:tag">
            <li <#if pageId==19|| pageId==20>class="active open" </#if>>
                <a href="#" class="menu-dropdown">
                    <i class="menu-icon glyphicon glyphicon-paperclip"></i>
                    <span class="menu-text"> 平台管理 </span>

                    <i class="menu-expand"></i>
                </a>
                <ul class="submenu">
                    <li <#if pageId==19>class="active" </#if>>
                        <a href="/oa/platform/index.html">
                            <span class="menu-text">添加平台</span>
                        </a>
                    </li>
                    <li <#if pageId==20>class="active" </#if>>
                        <a href="/oa/platform/list.html">
                            <span class="menu-text">平台查询</span>
                        </a>
                    </li>
                </ul>
            </li>
             </@shiro.hasPermission>
        <!--系统设置-->
             <@shiro.hasPermission name="system:tag">
            <li
            <#if pageId==21|| pageId==22|| pageId==23|| pageId==24|| pageId==32|| pageId==33|| pageId==38|| pageId==39>class="active open" </#if> >
                <a href="#" class="menu-dropdown">
                    <i class="menu-icon fa fa-cog"></i>
                    <span class="menu-text">系统设置</span>

                    <i class="menu-expand"></i>
                </a>

                <ul class="submenu">
            <@shiro.hasPermission name="permission:tag">
            <li <#if pageId==21|| pageId==22>class=" open" </#if>>
                <a href="#" class="menu-dropdown">
                                    <span class="menu-text">
                                        权限设置
                                    </span>
                    <i class="menu-expand"></i>
                </a>

                <ul class="submenu">
                    <li <#if pageId==21>class="active" </#if>>
                        <a href="/oa/permission/index.html">
                            <i class="menu-icon fa fa-rocket"></i>
                            <span class="menu-text">添加权限</span>
                        </a>
                    </li>
                    <li <#if pageId==22>class="active" </#if>>
                        <a href="/oa/permission/list.html">
                            <i class="menu-icon glyphicon glyphicon-stats"></i>
                            <span class="menu-text">查询权限</span>
                        </a>
                    </li>
                </ul>
            </li>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="role:tag">
                <li <#if pageId==23|| pageId==24>class=" open" </#if>>
                    <a href="#" class="menu-dropdown">
                                        <span class="menu-text">
                                            角色设置
                                        </span>
                        <i class="menu-expand"></i>
                    </a>

                    <ul class="submenu">
                        <li <#if pageId==23>class="active" </#if>>
                            <a href="/oa/role/index.html">
                                <i class="menu-icon fa fa-rocket"></i>
                                <span class="menu-text">添加角色</span>
                            </a>
                        </li>
                        <li <#if pageId==24>class="active" </#if>>
                            <a href="/oa/role/list.html">
                                <i class="menu-icon glyphicon glyphicon-stats"></i>
                                <span class="menu-text">查询角色</span>
                            </a>
                        </li>
                    </ul>
                </li>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="item:tag">
                 <li <#if pageId==32|| pageId==33>class=" open" </#if>>
                     <a href="#" class="menu-dropdown">
                                            <span class="menu-text">
                                                物品管理
                                            </span>
                         <i class="menu-expand"></i>
                     </a>

                     <ul class="submenu">
                         <li <#if pageId==23>class="active" </#if>>
                             <a href="/oa/item/index.html">
                                 <i class="menu-icon fa fa-rocket"></i>
                                 <span class="menu-text">添加物品</span>
                             </a>
                         </li>
                         <li <#if pageId==24>class="active" </#if>>
                             <a href="/oa/item/list.html">
                                 <i class="menu-icon glyphicon glyphicon-stats"></i>
                                 <span class="menu-text">查询物品</span>
                             </a>
                         </li>
                     </ul>
                 </li>
            </@shiro.hasPermission>
                    <@shiro.hasPermission name="feedbackType:tag">
                 <li <#if pageId==38|| pageId==39>class=" open" </#if>>
                     <a href="#" class="menu-dropdown">
                                            <span class="menu-text">
                                                反馈类型
                                            </span>
                         <i class="menu-expand"></i>
                     </a>

                     <ul class="submenu">
                         <li <#if pageId==38>class="active" </#if>>
                             <a href="/oa/feedback/type/index.html">
                                 <i class="menu-icon fa fa-rocket"></i>
                                 <span class="menu-text">添加类型</span>
                             </a>
                         </li>
                         <li <#if pageId==39>class="active" </#if>>
                             <a href="/oa/feedback/type/list.html">
                                 <i class="menu-icon glyphicon glyphicon-stats"></i>
                                 <span class="menu-text">查询类型</span>
                             </a>
                         </li>
                     </ul>
                 </li>
                    </@shiro.hasPermission>
                </ul>
            </li>
             </@shiro.hasPermission>
        <!--审核管理-->
        <!--Right to Left-->
             <@shiro.hasPermission name="check:tag">

          <li <#if pageId==6||pageId==40>class="open" </#if>>
              <a href="#" class="menu-dropdown">
                  <i class="menu-icon fa fa-align-right"></i>
                  <span class="menu-text">审核管理</span>

                  <i class="menu-expand"></i>
              </a>
              <ul class="submenu">
                  <li <#if pageId==6>class="active" </#if>>
                      <a href="/oa/check/list.html">
                          <span class="menu-text">审核记录</span>
                      </a>
                  </li>
                  <li <#if pageId==40>class="active" </#if>>
                      <a href="/oa/feedback/info/check.html">
                          <span class="menu-text">反馈记录</span>
                      </a>
                  </li>
              </ul>
          </li>
             </@shiro.hasPermission>
        <!--财务出纳-->
             <@shiro.hasPermission name="caiwu:tag">
               <li <#if pageId==26|| pageId==27|| pageId==28|| pageId==29|| pageId==34>class="open" </#if>>
                   <a href="#" class="menu-dropdown">
                       <i class="menu-icon fa fa-plane"></i>
                       <span class="menu-text">财务出纳</span>

                       <i class="menu-expand"></i>
                   </a>
                   <ul class="submenu">
                       <li <#if pageId==26>class="active" </#if>>
                           <a href="/oa/spending/checkList.html">
                               <span class="menu-text">日常开支</span>
                           </a>
                       </li>
                       <li <#if pageId==27>class="active" </#if>>
                           <a href="/oa/anchorSalaryAdvance/checkList.html">
                               <span class="menu-text">主播预支</span>
                           </a>
                       </li>
                       <li <#if pageId==28>class="active" </#if>>
                           <a href="/oa/withdraw/list.html">
                               <span class="menu-text">主播提现</span>
                           </a>
                       </li>
                       <li <#if pageId==29>class="active" </#if>>
                           <a href="/oa/personnelSalaryAdvance/checkList.html">
                               <span class="menu-text">员工预支</span>
                           </a>
                       </li>
                       <li <#if pageId==34>class="active" </#if>>
                           <a href="/oa/itemDebit/list.html">
                               <span class="menu-text">物品出库</span>
                           </a>
                       </li>
                   </ul>
               </li>
             </@shiro.hasPermission>
             <!--工资管理-->
             <@shiro.hasPermission name="salary:tag">
                <li <#if pageId==30|| pageId==31>class="active open" </#if>>
                    <a href="#" class="menu-dropdown">
                        <i class="menu-icon glyphicon glyphicon-credit-card"></i>
                        <span class="menu-text"> 工资管理 </span>

                        <i class="menu-expand"></i>
                    </a>
                    <ul class="submenu">
                        <li <#if pageId==30>class="active" </#if>>
                            <a href="/oa/personnelSalary/list.html">
                                <span class="menu-text">工作人员工资</span>
                            </a>
                        </li>
                        <li <#if pageId==31>class="active" </#if>>
                            <a href="/oa/anchorSalary/list/momo.html">
                                <span class="menu-text">陌陌主播工资</span>
                            </a>
                        </li>
                    </ul>
                </li>
             </@shiro.hasPermission>
             <@shiro.hasPermission name="union:tag">
                <li <#if pageId==35|| pageId==36>class="active open" </#if>>
                    <a href="#" class="menu-dropdown">
                        <i class="menu-icon fa fa-laptop"></i>
                        <span class="menu-text"> 工会管理 </span>

                        <i class="menu-expand"></i>
                    </a>
                    <ul class="submenu">
                        <li <#if pageId==35>class="active" </#if>>
                        <#--https://live-star.immomo.com/guild/#/signed&ndash-->
                            <a href="/momoUnion.html" target="" class="menu-dropdown">
                                <i class=" "></i>
                                <span class="menu-text"> 陌陌工会 </span>
                            </a>
                        </li>
                        <li <#if pageId==36>class="active" </#if>>
                        <#--https://live-star.immomo.com/guild/#/signed&ndash-->
                            <a href="/KKUnion.html" target="_blank" class="menu-dropdown">
                                <i class=" "></i>
                                <span class="menu-text"> 唱响家族 </span>
                            </a>
                            <a href="http://www.cdrysj.com/oa/login/index" target="_blank" class="menu-dropdown">
                                <i class=" "></i>
                                <span class="menu-text"> 官网后台 </span>
                            </a>
                        </li>
                    </ul>
                </li>
             </@shiro.hasPermission>

    <!--反馈-->
             <@shiro.hasPermission name="feedback:tag">
                <li <#if pageId==41|| pageId==42>class="open" </#if>>
                    <a href="#" class="menu-dropdown">
                        <i class="menu-icon fa fa-comment"></i>
                        <span class="menu-text"> 反馈管理 </span>
                        <i class="menu-expand"></i>
                    </a>
                    <ul class="submenu">
                        <li <#if pageId==42>class="active" </#if>>
                            <a href="/oa/feedback/info/index.html">
                                <span class="menu-text">反馈添加</span>
                            </a>
                        </li>
                        <li <#if pageId==41>class="active" </#if>>
                            <a href="/oa/feedback/info/list.html">
                                <span class="menu-text">反馈查询</span>
                            </a>
                        </li>
                    </ul>
                </li>
             </@shiro.hasPermission>
    <!--通知管理-->

      <@shiro.hasPermission name="notice:tag">
            <li
            <#if pageId==44|| pageId==45|| pageId==46|| pageId==47>class="active open" </#if> >
                <a href="#" class="menu-dropdown">
                    <i class="menu-icon fa  fa-bullhorn"></i>
                    <span class="menu-text">通知管理</span>

                    <i class="menu-expand"></i>
                </a>

                <ul class="submenu">
            <@shiro.hasPermission name="noticeMessage:tag">
                <li <#if pageId==44|| pageId==45>class=" open" </#if>>
                <a href="#" class="menu-dropdown">
                                    <span class="menu-text">
                                        短信通知
                                    </span>
                    <i class="menu-expand"></i>
                </a>
                <ul class="submenu">
                    <li <#if pageId==44>class="active" </#if>>
                        <a href="/oa/notice/message/index.html">
                            <i class="menu-icon fa fa-rocket"></i>
                            <span class="menu-text">通知发送</span>
                        </a>
                    </li>
                    <li <#if pageId==45>class="active" </#if>>
                        <a href="/oa/notice/message/list.html">
                            <i class="menu-icon glyphicon glyphicon-stats"></i>
                            <span class="menu-text">通知记录</span>
                        </a>
                    </li>
                </ul>
            </li>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="noticeSystem:tag">
                <li <#if pageId==46|| pageId==47>class=" open" </#if>>
                    <a href="#" class="menu-dropdown">
                                        <span class="menu-text">
                                            系统通知
                                        </span>
                        <i class="menu-expand"></i>
                    </a>

                    <ul class="submenu">
                        <li <#if pageId==46>class="active" </#if>>
                            <a href="/oa/notice/system/index.html">
                                <i class="menu-icon fa fa-rocket"></i>
                                <span class="menu-text">添加发送</span>
                            </a>
                        </li>
                        <li <#if pageId==47>class="active" </#if>>
                            <a href="/oa/notice/system/list.html">
                                <i class="menu-icon glyphicon glyphicon-stats"></i>
                                <span class="menu-text">记录查询</span>
                            </a>
                        </li>
                    </ul>
                </li>
            </@shiro.hasPermission>

                </ul>
            </li>
      </@shiro.hasPermission>
    <!--个人通知-->
     <@shiro.hasPermission name="noticeUser:tag">
        <li <#if pageId==43>class="active" </#if>>
            <a href="/oa/notice/system/list/user.html">
                <i class="menu-icon glyphicon glyphicon-bell"></i>
                <span class="menu-text"> 通知消息 </span>
            </a>
        </li>
     </@shiro.hasPermission>

    </ul>
</div>
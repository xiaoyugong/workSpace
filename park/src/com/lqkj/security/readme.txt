1、容器启动(MyInvocationSecurityMetadataSource：loadResourceDefine加载系统资源与权限列表)
2、用户发出请求
3、过滤器拦截(MyFilterSecurityInterceptor:doFilter)
4、取得请求资源所需权限(MyInvocationSecurityMetadataSource:getAttributes)
5、匹配用户拥有权限和请求权限(MyAccessDecisionManager:decide)，如果用户没有相应的权限，执行第6步，否则执行第7步
6、登录
7、验证并授权(MyUserDetailService:loadUserByUsername)
8、重复4,5

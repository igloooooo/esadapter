<%@include file="/libs/foundation/global.jsp"%>
<%@page session="false" import="com.adobe.granite.xss.XSSAPI"%>
<%@page import="com.day.cq.commons.Externalizer"%>
<%@page import="com.day.cq.wcm.api.WCMMode"%><%
  /* Services */
  final XSSAPI xss = sling.getService(XSSAPI.class);
  final Externalizer externalizer = resourceResolver.adaptTo(Externalizer.class);
  /* LinkRewritingTransformerFactory works only for pages that contain rewriterEnabled attribute.
  This is to ensure that cq5 admin pages are not externalized */
  slingRequest.setAttribute("rewriterEnabled","true");

%>

<c:set var="isEditMode"><%=WCMMode.fromRequest(request) == WCMMode.EDIT %></c:set>

<c:set var="site" value="<%= pageProperties.getInherited("site", "") %>" scope="request" />
<c:set var="isAuthor" value="<%= sling.getService(org.apache.sling.settings.SlingSettingsService.class).getRunModes().contains("author") %>" scope="request" />

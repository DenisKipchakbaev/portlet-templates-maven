package me.kipchakbaev.liferay.springmvc.controller;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;

import me.kipchakbaev.liferay.springmvc.controller.keys.Views;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.ResourceResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

/**
 * 
 * @author <a href="mailto:kipchakbaev@gmail.com">Denis Kipchakbaev</a>
 *
 */
@Controller
@RequestMapping("VIEW")
public class PortletViewController {
    private static final Log LOGGER = LogFactoryUtil.getLog(PortletViewController.class);

    @RenderMapping
    public String defaultView() {
    	if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Default View");
        }

        return Views.DEFAULT_VIEW;
    }

    @RenderMapping(params = "render=alternative-view")
    public String alternativeView() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Alternative view");
        }

        return Views.ALTERNATIVE_VIEW;
    }

    @ActionMapping(params = "action=action-one")
    public void actionOne() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Action one");
        }

        // Returns control to default view
    }

    @ActionMapping(params = "action=action-two")
    public void actionTwo(ActionResponse actionResponse) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Action two");
        }

        // Returns control to alternative view
        actionResponse.setRenderParameter("render", "alternative-view");
    }

    @ResourceMapping(value = "resource-one")
    public void resourceOne(ResourceResponse resourceResponse) throws PortletException {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Resource one");
        }

        try {
            resourceResponse.setContentType("text/html");
            PrintWriter writer = resourceResponse.getWriter();
            writer.println("<p>This request handle complete response. This is useful to return JSON, images, files or any other resources that are needed by our portlets</p>");
        } catch (IOException e) {
            throw new PortletException(e);
        }
    }
    
    @RenderMapping(params = "render=another-jsp-dir")
    public String anotherJspDir(Model model) {
    	model.addAttribute("releaseInfo", ReleaseInfo.getReleaseInfo());
    	model.addAttribute("serverInfo", ReleaseInfo.getServerInfo());
    	if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Sub dir View");
        }

        return Views.ANOTHER_DIR_VIEW;
    }
}
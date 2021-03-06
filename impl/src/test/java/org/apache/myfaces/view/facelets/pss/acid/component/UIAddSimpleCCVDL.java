/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.view.facelets.pss.acid.component;

import java.util.HashMap;
import java.util.Map;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PreRenderViewEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.faces.view.ViewDeclarationLanguage;

@FacesComponent(value = "com.myapp.UIAddSimpleCCVDL")
public class UIAddSimpleCCVDL extends UIComponentBase implements
        SystemEventListener
{

    //
    // Constructor
    //

    public UIAddSimpleCCVDL()
    {
        setRendererType(null);

        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();

        root.subscribeToViewEvent(PreRenderViewEvent.class, this);
    }

    //
    // Public methods
    //

    @Override
    public String getFamily()
    {

        return "com.myapp";
    }

    public boolean isListenerForSource(Object source)
    {

        return (source instanceof UIViewRoot);
    }

    public void processEvent(SystemEvent event) throws AbortProcessingException
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (!facesContext.isPostback())
        {
            ViewDeclarationLanguage vdl = facesContext.getApplication().
                getViewHandler().getViewDeclarationLanguage(
                    facesContext, facesContext.getViewRoot().getViewId());

            Map<String, Object> attributes = new HashMap<String, Object>();
            UIComponent component = vdl.createComponent(facesContext, 
                "http://java.sun.com/jsf/composite/testComposite", 
                "dynComp_1", attributes);
            UIOutput text = (UIOutput) facesContext.getApplication().
                createComponent(UIOutput.COMPONENT_TYPE);
            text.setValue("Dynamically added header");
            component.getFacets().put("header", text);
            
            getChildren().add(component);
        }
    }
}

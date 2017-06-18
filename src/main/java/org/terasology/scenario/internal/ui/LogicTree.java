/*
 * Copyright 2017 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.scenario.internal.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.rendering.nui.widgets.treeView.Tree;
import org.terasology.scenario.components.ExpandedComponent;
import org.terasology.scenario.components.TriggerNameComponent;


public class LogicTree extends Tree<LogicTreeValue> {

    private HubToolScreen hubToolScreen;

    private static final Logger logger = LoggerFactory.getLogger(LogicTree.class);
    public LogicTree(HubToolScreen hubToolScreen) {
        this.hubToolScreen = hubToolScreen;
    }

    public LogicTree(LogicTreeValue value, HubToolScreen hubToolScreen) {
        setValue(value);
        this.hubToolScreen = hubToolScreen;
    }

    @Override
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);
        if (expanded) {
            if (value.getEntity() != null) {
                switch (value.getValueType()) {
                    //If it is a name(event/conditional/action) then it needs to find the matching entity from the trigger
                    case EVENT_NAME:
                        hubToolScreen.getEntity().getComponent(ExpandedComponent.class).expandedList.add(this.getValue().getEntity().getComponent(TriggerNameComponent.class).entityForEvent);
                        hubToolScreen.getEntity().saveComponent(hubToolScreen.getEntity().getComponent(ExpandedComponent.class));
                        break;
                    case CONDITIONAL_NAME:
                        hubToolScreen.getEntity().getComponent(ExpandedComponent.class).expandedList.add(this.getValue().getEntity().getComponent(TriggerNameComponent.class).entityForCondition);
                        hubToolScreen.getEntity().saveComponent(hubToolScreen.getEntity().getComponent(ExpandedComponent.class));
                        break;
                    case ACTION_NAME:
                        hubToolScreen.getEntity().getComponent(ExpandedComponent.class).expandedList.add(this.getValue().getEntity().getComponent(TriggerNameComponent.class).entityForAction);
                        hubToolScreen.getEntity().saveComponent(hubToolScreen.getEntity().getComponent(ExpandedComponent.class));
                        break;
                    default:
                        hubToolScreen.getEntity().getComponent(ExpandedComponent.class).expandedList.add(this.getValue().getEntity());
                        hubToolScreen.getEntity().saveComponent(hubToolScreen.getEntity().getComponent(ExpandedComponent.class));
                        break;
                }

            }
        }
        else {
            if (value.getEntity() != null) {
                switch (value.getValueType()) {
                    //If it is a name(event/conditional/action) then it needs to find the matching entity from the trigger
                    case EVENT_NAME:
                        hubToolScreen.getEntity().getComponent(ExpandedComponent.class).expandedList.remove(this.getValue().getEntity().getComponent(TriggerNameComponent.class).entityForEvent);
                        hubToolScreen.getEntity().saveComponent(hubToolScreen.getEntity().getComponent(ExpandedComponent.class));
                        break;
                    case CONDITIONAL_NAME:
                        hubToolScreen.getEntity().getComponent(ExpandedComponent.class).expandedList.remove(this.getValue().getEntity().getComponent(TriggerNameComponent.class).entityForCondition);
                        hubToolScreen.getEntity().saveComponent(hubToolScreen.getEntity().getComponent(ExpandedComponent.class));
                        break;
                    case ACTION_NAME:
                        hubToolScreen.getEntity().getComponent(ExpandedComponent.class).expandedList.remove(this.getValue().getEntity().getComponent(TriggerNameComponent.class).entityForAction);
                        hubToolScreen.getEntity().saveComponent(hubToolScreen.getEntity().getComponent(ExpandedComponent.class));
                        break;
                    default:
                        hubToolScreen.getEntity().getComponent(ExpandedComponent.class).expandedList.remove(this.getValue().getEntity());
                        hubToolScreen.getEntity().saveComponent(hubToolScreen.getEntity().getComponent(ExpandedComponent.class));
                        break;
                }
            }
        }
    }

    public void setExpandedNoEntity(boolean expanded) {
        super.setExpanded(expanded);
    }

    @Override
    public void addChild(LogicTreeValue childValue){
        addChild(new LogicTree(childValue, hubToolScreen));
    }

    @Override
    public Tree<LogicTreeValue> copy(){
        throw new UnsupportedOperationException();
    }
}

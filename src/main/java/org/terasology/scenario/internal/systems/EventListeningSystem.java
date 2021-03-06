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
package org.terasology.scenario.internal.systems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.entitySystem.entity.EntityManager;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterMode;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.logic.health.DoDestroyEvent;
import org.terasology.logic.players.event.OnPlayerRespawnedEvent;
import org.terasology.logic.players.event.OnPlayerSpawnedEvent;
import org.terasology.registry.In;
import org.terasology.scenario.components.ScenarioComponent;
import org.terasology.scenario.internal.events.scenarioEvents.DoDestroyScenarioEvent;
import org.terasology.scenario.internal.events.scenarioEvents.PlayerEnterRegionEvent;
import org.terasology.scenario.internal.events.scenarioEvents.PlayerRespawnScenarioEvent;
import org.terasology.scenario.internal.events.scenarioEvents.PlayerSpawnScenarioEvent;

/**
 * System that listened for terasology events and converts them into scenario events and sends them to the active scenario
 */
@RegisterSystem(RegisterMode.AUTHORITY)
public class EventListeningSystem extends BaseComponentSystem {
    @In
    private EntityManager entityManager;

    private Logger logger = LoggerFactory.getLogger(EventListeningSystem.class);

    @ReceiveEvent
    public void onPlayerRejoinEvent(OnPlayerRespawnedEvent event, EntityRef entity) {
        if (entityManager.getEntitiesWith(ScenarioComponent.class).iterator().hasNext()) {
            EntityRef scenario = entityManager.getEntitiesWith(ScenarioComponent.class).iterator().next();
            if (scenario == null) {
                return;
            }
            scenario.send(new PlayerRespawnScenarioEvent(entity));
        }
    }

    @ReceiveEvent
    public void onPlayerSpawnEvent(OnPlayerSpawnedEvent event, EntityRef entity) {
        if (entityManager.getEntitiesWith(ScenarioComponent.class).iterator().hasNext()) {
            EntityRef scenario = entityManager.getEntitiesWith(ScenarioComponent.class).iterator().next();
            if (scenario == null) {
                return;
            }
            scenario.send(new PlayerSpawnScenarioEvent(entity));
        }
    }

    @ReceiveEvent
    public void onDoDestroyEvent(DoDestroyEvent event, EntityRef entity) {
        if (entityManager.getEntitiesWith(ScenarioComponent.class).iterator().hasNext()) {
            EntityRef scenario = entityManager.getEntitiesWith(ScenarioComponent.class).iterator().next();
            if (scenario == null) {
                return;
            }
            scenario.send(new DoDestroyScenarioEvent(event.getInstigator(), event.getDirectCause(), event.getDamageType(), entity));
        }
    }



}

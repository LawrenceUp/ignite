/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.internal.processors.query.calcite.prepare;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.ignite.internal.processors.query.GridQueryFieldMetadata;
import org.apache.ignite.internal.processors.query.calcite.metadata.MappingService;
import org.apache.ignite.internal.processors.query.calcite.metadata.NodesMapping;

/**
 * Regular query or DML
 */
public interface MultiStepPlan extends QueryPlan {
    /**
     * @return Query fragments.
     */
    List<Fragment> fragments();

    /**
     * @return Row metadata.
     */
    List<GridQueryFieldMetadata> fieldsMetadata();

    /**
     * @param fragment Fragment.
     * @return Mapping for a given fragment.
     */
    NodesMapping fragmentMapping(Fragment fragment);

    /** */
    NodesMapping targetMapping(Fragment fragment);

    /** */
    Map<Long, List<UUID>> remoteSources(Fragment fragment);

    /**
     * Inits query fragments.
     *
     * @param mappingService Mapping service.
     * @param ctx Planner context.
     */
    void init(MappingService mappingService, PlanningContext ctx);
}

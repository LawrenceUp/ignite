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

package org.apache.ignite.internal.processors.query.calcite.rel;

import java.util.List;
import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelOptCost;
import org.apache.calcite.plan.RelOptPlanner;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelDistribution;
import org.apache.calcite.rel.RelInput;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.SetOp;
import org.apache.calcite.rel.core.Union;
import org.apache.calcite.rel.metadata.RelMetadataQuery;

/**
 *
 */
public class IgniteUnionAll extends Union implements IgniteRel {
    /** */
    public IgniteUnionAll(RelOptCluster cluster, RelTraitSet traits, List<RelNode> inputs) {
        super(cluster, traits, inputs, true);
    }

    /** */
    public IgniteUnionAll(RelInput input) {
        this(
            input.getCluster(),
            input.getTraitSet().replace(IgniteConvention.INSTANCE),
            input.getInputs());
    }

    /** {@inheritDoc} */
    @Override public SetOp copy(RelTraitSet traitSet, List<RelNode> inputs, boolean all) {
        assert all;

        return new IgniteUnionAll(getCluster(), traitSet, inputs);
    }

    /** {@inheritDoc} */
    @Override public <T> T accept(IgniteRelVisitor<T> visitor) {
        return visitor.visit(this);
    }

    /** {@inheritDoc} */
    @Override public RelOptCost computeSelfCost(RelOptPlanner planner, RelMetadataQuery mq) {
        if (distribution().getType() == RelDistribution.Type.ANY)
            return planner.getCostFactory().makeInfiniteCost();

        return super.computeSelfCost(planner, mq);
    }
}

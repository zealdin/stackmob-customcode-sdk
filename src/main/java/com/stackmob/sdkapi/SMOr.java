/**
 * Copyright 2012 StackMob
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stackmob.sdkapi;

import java.util.List;

/**
 * An "or" query. subclauses will be joined together with "or"
 */
public class SMOr extends SMCondition {

    private final List<SMCondition> clauses;

    /**
     * Create an new SMOr query.
     * @param clauses The clauses to be joined with "or"
     */
    public SMOr(List<SMCondition> clauses) {
        this.clauses = clauses;
    }

    /**
     * Get the clauses
     * @return
     */
    public List<SMCondition> getClauses() {
        return clauses;
    }
}

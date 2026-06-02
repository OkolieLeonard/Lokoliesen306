# Project Reflection: Refactoring the Customer Processing Routine

### 1. Architectural Strategy for Achieving Functional Cohesion
The legacy `processCustomer` method suffered from poor architectural design, combining input checks, mathematical calculations, string building, and console output into a single, tightly coupled block. 

To achieve functional cohesion, the routine was broken down so that every component has exactly one point of responsibility. The monolithic block was refactored into the following isolated sub-routines:
* `validateInputs`: Focuses purely on protecting runtime integrity by trapping negative order metrics or out-of-bounds types.
* `calculateSum`: Responsible only for the mathematical aggregation of the order array elements.
* `determineDiscountRate`: Maps customer classifications to numerical rates without factoring in the broader price calculations.
* `calculateTotal`: Executes the final financial subtraction logic.
* `formatCustomerMessage`: Converts underlying data states into a user-facing string block.

To maximize readability, computational methods returning an explicit value use a descriptive noun phrase convention (`calculateSum`), while methods handling assertions or operations utilize clear `verb+object` syntax (`validateInputs`).

### 2. Analysis of Parameter Passing Constraints
The original snippet attempted to alter the calling scope's state via a local mutation at the end of the method (`d = total;`). Because both Java and Python pass values down isolated stack frames (passing primitive values directly, and reference variables by-value), local reassignments of this nature are completely invisible to the outer scope once the method returns. The calculated total was effectively lost.

To resolve this parameter passing issue safely, the design abandoned implicit parameter modifications. Instead, the refactored architecture relies on an explicit return statement, returning the final computed total directly to the caller.

### 3. Execution Behavior Under Pass-by-Value-Result
If this routine were executed in a language that natively supports pass-by-value-result (such as Ada), the behavior of variable `d` would shift dramatically:
1. **Copy-In Phase:** Upon entering the method, the exact value of the caller's variable would be copied into a localized parameter variable inside the function frame.
2. **Local Execution:** The routine would execute its logic on this independent local variable copy.
3. **Copy-Out Phase:** At the exact moment the function terminates, the final value held by that local variable would be written back into the memory address of the caller's original variable.

Thus, while the assignment fails to propagate in standard Java or Python due to pass-by-value rules, a pass-by-value-result environment would successfully overwrite the caller's variable with the final total at the end of the call.

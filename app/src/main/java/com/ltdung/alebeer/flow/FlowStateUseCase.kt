package com.ltdung.alebeer.flow

import kotlinx.coroutines.flow.Flow

interface UseCase<I, O> {
    operator fun invoke(input: I): O
}

typealias FlowStateUseCase<I, O> = UseCase<I, Flow<State<O>>>

class None
data class Params2<P0, P1>(val p0: P0, val p1: P1)
data class Params3<P0, P1, P2>(val p0: P0, val p1: P1, val p2: P2)
data class Params4<P0, P1, P2, P3>(val p0: P0, val p1: P1, val p2: P2, val p3: P3)
data class Params5<P0, P1, P2, P3, P4>(val p0: P0, val p1: P1, val p2: P2, val p3: P3, val p4: P4)
data class Params6<P0, P1, P2, P3, P4, P5>(
    val p0: P0,
    val p1: P1,
    val p2: P2,
    val p3: P3,
    val p4: P4,
    val p5: P5
)

data class Params7<P0, P1, P2, P3, P4, P5, P6>(
    val p0: P0,
    val p1: P1,
    val p2: P2,
    val p3: P3,
    val p4: P4,
    val p5: P5,
    val p6: P6
)

data class Params8<P0, P1, P2, P3, P4, P5, P6, P7>(
    val p0: P0,
    val p1: P1,
    val p2: P2,
    val p3: P3,
    val p4: P4,
    val p5: P5,
    val p6: P6,
    val p7: P7
)

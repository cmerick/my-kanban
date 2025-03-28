import { useRef, useState } from 'react';

type AsyncFuc = (...args: never[]) => Promise<unknown>;
type SyncFuc = (...args: never[]) => unknown;

type Fuc = SyncFuc | AsyncFuc;

export default function usePreventAsyncFunction<T extends Fuc>(
    fun: T,
    controlledState = true,
) {
    const [unlockFunction, setUnlockFunction] = useState(true);
    const unlockFunctionRef = useRef(true);

    const safeFunction = async (...args: never[]) => {
        if (isUnlockFunction()) {
            try {
                setIsUnlockFunction(false);
                return await fun(...args);
            } finally {
                setIsUnlockFunction(true);
            }
        }
    };

    const isUnlockFunction = () => {
        return controlledState ? unlockFunction : unlockFunctionRef.current;
    };

    const setIsUnlockFunction = (state: boolean) => {
        if (controlledState) {
            setUnlockFunction(state);
        } else {
            unlockFunctionRef.current = state;
        }
    };

    return {
        safeFunction: safeFunction as unknown as T,
        isUnlock: isUnlockFunction(),
    };
}

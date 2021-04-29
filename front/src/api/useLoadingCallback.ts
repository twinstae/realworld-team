import { useState } from "react";

export type IsLoading = boolean;
export type LoadingCallback<T extends (...args: any[]) => Promise<any>> = (
    ...args: Parameters<T>
  ) => ReturnType<T>;
export type ResetFunc = () => void;
export type AnyError = any;

export const useLoadingCallback = <T extends (...args: any[]) => Promise<any>>(
  callback: T
): [LoadingCallback<T>, IsLoading, AnyError | undefined, ResetFunc] => {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<any | undefined>();

  const handleCallback = async (...args: Parameters<T>) => {
    setError(undefined);
    setIsLoading(true);

    return await callback(...args)
      .catch((error) => {
        setError(error);
        return undefined;
      })
      .finally(()=>{
        setIsLoading(false);
      });
  };

  const reset = () => {
    setIsLoading(false);
    setError(undefined);
  };

  return [handleCallback as LoadingCallback<T>, isLoading, error, reset];
};

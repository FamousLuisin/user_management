type HttpMethod = "GET" | "POST" | "PUT" | "PATCH" | "DELETE";

export async function fetchAPI<TRequest = unknown, TResponse = unknown>(
  url: string,
  method: HttpMethod,
  options?: {
    headers?: Record<string, string>;
    body?: TRequest;
  }
): Promise<TResponse | null> {
  const response = await fetch(url, {
    method,
    headers: {
      "Content-Type": "application/json",
      ...options?.headers,
    },
    body: options?.body ? JSON.stringify(options.body) : undefined,
  });

  if (!response.ok) {
    throw new Error(`${response.status}`);
  }

  if (response.status == 204) return null;

  return response.json() as Promise<TResponse>;
}
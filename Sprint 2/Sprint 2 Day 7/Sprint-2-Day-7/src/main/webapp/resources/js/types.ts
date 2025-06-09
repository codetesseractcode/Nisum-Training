/**
 * TypeScript Types for Todo Application
 * Demonstrating TypeScript concepts required for the assignment
 */

// Basic Types
export type TodoId = number;
export type TodoTitle = string;
export type TodoDescription = string;
export type TodoStatus = boolean;
export type TodoDate = Date | string | null;

// Enum for Todo Priority
export enum TodoPriority {
  HIGH = 'HIGH',
  MEDIUM = 'MEDIUM',
  LOW = 'LOW'
}

// Tuple example - [id, title, completed]
export type TodoSummary = [TodoId, TodoTitle, TodoStatus];

// Interface for Todo
export interface ITodo {
  id: TodoId;
  title: TodoTitle;
  description: TodoDescription;
  completed: TodoStatus;
  createdDate: TodoDate;
  dueDate: TodoDate;
  priority: TodoPriority;
}

// Optional properties with null
export interface TodoCreateRequest {
  title: TodoTitle;
  description?: TodoDescription;
  dueDate?: TodoDate;
  priority?: TodoPriority;
}

// Union Types
export type TodoFilter = 'all' | 'completed' | 'pending';

// Intersection Types
export type TodoWithMetadata = ITodo & {
  lastModified?: Date;
  tags?: string[];
};

// Type Aliases with literal types
export type PriorityColors = {
  [key in TodoPriority]: string;
};

// Nullable Types
export type NullableTodo = ITodo | null;

// Type Guard Functions
export function isTodo(obj: any): obj is ITodo {
  return obj &&
    typeof obj.id === 'number' &&
    typeof obj.title === 'string' &&
    typeof obj.completed === 'boolean';
}

// Discriminated Union (Tagged Union)
export type TodoAction =
  | { type: 'ADD'; payload: TodoCreateRequest }
  | { type: 'UPDATE'; payload: ITodo }
  | { type: 'DELETE'; payload: TodoId }
  | { type: 'TOGGLE'; payload: TodoId }
  | { type: 'FILTER'; payload: TodoFilter };

// Conditional Types
export type TodoResponseType<T> = T extends TodoId ? ITodo : T extends 'all' ? ITodo[] : never;

// Generic Type
export interface ApiResponse<T> {
  data: T;
  status: number;
  message: string;
}

// Mapped Type
export type ReadonlyTodo = {
  readonly [K in keyof ITodo]: ITodo[K];
};

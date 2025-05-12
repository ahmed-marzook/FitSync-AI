import type { Activity } from "./Activity";
import type { Pageable } from "./Pageable";
import type { Sort } from "./Sort";

// Define the response type
export interface ActivityResponse {
  content: Activity[];
  pageable: Pageable;
  last: boolean;
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
  sort: Sort;
  numberOfElements: number;
  first: boolean;
  empty: boolean;
}

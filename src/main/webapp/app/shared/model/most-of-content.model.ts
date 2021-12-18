import dayjs from 'dayjs';
import { IMostOfContValues } from 'app/shared/model/most-of-cont-values.model';

export interface IMostOfContent {
  id?: number;
  urlOriginal?: string | null;
  contentText?: string | null;
  postTime?: string | null;
  numberLike?: number | null;
  numberComment?: number | null;
  mostOfContValues?: IMostOfContValues[] | null;
}

export const defaultValue: Readonly<IMostOfContent> = {};

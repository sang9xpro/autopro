import dayjs from 'dayjs';
import { ILoggersValues } from 'app/shared/model/loggers-values.model';

export interface ILoggers {
  id?: number;
  status?: string | null;
  logDetailContentType?: string | null;
  logDetail?: string | null;
  lastUpdate?: string | null;
  loggersValues?: ILoggersValues[] | null;
}

export const defaultValue: Readonly<ILoggers> = {};

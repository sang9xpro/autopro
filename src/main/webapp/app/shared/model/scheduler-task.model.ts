import dayjs from 'dayjs';
import { ISchedulerTaskValues } from 'app/shared/model/scheduler-task-values.model';
import { SchedulerStatus } from 'app/shared/model/enumerations/scheduler-status.model';

export interface ISchedulerTask {
  id?: number;
  startTime?: string | null;
  endTime?: string | null;
  countFrom?: number | null;
  countTo?: number | null;
  point?: number | null;
  lastUpdate?: string | null;
  owner?: string | null;
  status?: SchedulerStatus | null;
  schedulerTaskValues?: ISchedulerTaskValues[] | null;
}

export const defaultValue: Readonly<ISchedulerTask> = {};

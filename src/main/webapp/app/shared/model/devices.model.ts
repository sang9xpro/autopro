import dayjs from 'dayjs';
import { IDeviceValues } from 'app/shared/model/device-values.model';
import { DeviceType } from 'app/shared/model/enumerations/device-type.model';
import { DeviceStatus } from 'app/shared/model/enumerations/device-status.model';

export interface IDevices {
  id?: number;
  ipAddress?: string | null;
  macAddress?: string | null;
  os?: string | null;
  deviceType?: DeviceType | null;
  status?: DeviceStatus | null;
  lastUpdate?: string | null;
  owner?: string | null;
  deviceValues?: IDeviceValues[] | null;
}

export const defaultValue: Readonly<IDevices> = {};

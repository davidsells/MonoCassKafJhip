import { ICategory } from 'app/shared/model/category.model';

export interface IAudioItem {
  id?: string;
  slug?: string | null;
  transaction?: string | null;
  itemDescription?: string | null;
  categoryName?: string | null;
  price?: string | null;
  location?: string | null;
  dateofadd?: string | null;
  link?: string | null;
  image?: string | null;
  category?: ICategory | null;
}

export const defaultValue: Readonly<IAudioItem> = {};

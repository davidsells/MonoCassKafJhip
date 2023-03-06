import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AudioItem from './audio-item';
import AudioItemDetail from './audio-item-detail';
import AudioItemUpdate from './audio-item-update';
import AudioItemDeleteDialog from './audio-item-delete-dialog';

const AudioItemRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AudioItem />} />
    <Route path="new" element={<AudioItemUpdate />} />
    <Route path=":id">
      <Route index element={<AudioItemDetail />} />
      <Route path="edit" element={<AudioItemUpdate />} />
      <Route path="delete" element={<AudioItemDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AudioItemRoutes;

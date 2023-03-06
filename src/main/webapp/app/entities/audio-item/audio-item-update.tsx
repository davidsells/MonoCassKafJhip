import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICategory } from 'app/shared/model/category.model';
import { getEntities as getCategories } from 'app/entities/category/category.reducer';
import { IAudioItem } from 'app/shared/model/audio-item.model';
import { getEntity, updateEntity, createEntity, reset } from './audio-item.reducer';

export const AudioItemUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const categories = useAppSelector(state => state.category.entities);
  const audioItemEntity = useAppSelector(state => state.audioItem.entity);
  const loading = useAppSelector(state => state.audioItem.loading);
  const updating = useAppSelector(state => state.audioItem.updating);
  const updateSuccess = useAppSelector(state => state.audioItem.updateSuccess);

  const handleClose = () => {
    navigate('/audio-item');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCategories({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...audioItemEntity,
      ...values,
      category: categories.find(it => it.id.toString() === values.category.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...audioItemEntity,
          category: audioItemEntity?.category?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="monoCassandraApp.audioItem.home.createOrEditLabel" data-cy="AudioItemCreateUpdateHeading">
            Create or edit a Audio Item
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="audio-item-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Slug" id="audio-item-slug" name="slug" data-cy="slug" type="text" />
              <ValidatedField label="Transaction" id="audio-item-transaction" name="transaction" data-cy="transaction" type="text" />
              <ValidatedField
                label="Item Description"
                id="audio-item-itemDescription"
                name="itemDescription"
                data-cy="itemDescription"
                type="text"
              />
              <ValidatedField label="Category Name" id="audio-item-categoryName" name="categoryName" data-cy="categoryName" type="text" />
              <ValidatedField label="Price" id="audio-item-price" name="price" data-cy="price" type="text" />
              <ValidatedField label="Location" id="audio-item-location" name="location" data-cy="location" type="text" />
              <ValidatedField label="Dateofadd" id="audio-item-dateofadd" name="dateofadd" data-cy="dateofadd" type="text" />
              <ValidatedField label="Link" id="audio-item-link" name="link" data-cy="link" type="text" />
              <ValidatedField label="Image" id="audio-item-image" name="image" data-cy="image" type="text" />
              <ValidatedField id="audio-item-category" name="category" data-cy="category" label="Category" type="select">
                <option value="" key="0" />
                {categories
                  ? categories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/audio-item" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AudioItemUpdate;

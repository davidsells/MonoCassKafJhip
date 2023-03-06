import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './audio-item.reducer';

export const AudioItemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const audioItemEntity = useAppSelector(state => state.audioItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="audioItemDetailsHeading">Audio Item</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{audioItemEntity.id}</dd>
          <dt>
            <span id="slug">Slug</span>
          </dt>
          <dd>{audioItemEntity.slug}</dd>
          <dt>
            <span id="transaction">Transaction</span>
          </dt>
          <dd>{audioItemEntity.transaction}</dd>
          <dt>
            <span id="itemDescription">Item Description</span>
          </dt>
          <dd>{audioItemEntity.itemDescription}</dd>
          <dt>
            <span id="categoryName">Category Name</span>
          </dt>
          <dd>{audioItemEntity.categoryName}</dd>
          <dt>
            <span id="price">Price</span>
          </dt>
          <dd>{audioItemEntity.price}</dd>
          <dt>
            <span id="location">Location</span>
          </dt>
          <dd>{audioItemEntity.location}</dd>
          <dt>
            <span id="dateofadd">Dateofadd</span>
          </dt>
          <dd>{audioItemEntity.dateofadd}</dd>
          <dt>
            <span id="link">Link</span>
          </dt>
          <dd>{audioItemEntity.link}</dd>
          <dt>
            <span id="image">Image</span>
          </dt>
          <dd>{audioItemEntity.image}</dd>
          <dt>Category</dt>
          <dd>{audioItemEntity.category ? audioItemEntity.category.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/audio-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/audio-item/${audioItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AudioItemDetail;

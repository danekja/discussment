INSERT INTO `discussment_category` (id, name) VALUES
  (-1, "1 Category"),
  (-2, "2 Category"),
  (-3, "3 Category");

INSERT INTO `discussment_topic` (id, name, category_id) VALUES
  (-1, "1 Topic", -1),
  (-2, "2 Topic", -1),
  (-3, "3 Topic", -2);

INSERT INTO `discussment_discussion` (id, name, topic_id) VALUES
  (-1, "1 Discussion", -1),
  (-2, "2 Discussion", -1),
  (-3, "3 Discussion", -2);

INSERT INTO `discussment_post` (id, text, created, user_id, discussion_id, likes, dislikes, is_disabled, level, parent_post_id)
VALUES
  (-1, "1 Post", "2018-01-01 14:01:33", "testuser1", -1, 2, 0, false, 0, NULL),
  (-2, "2 Post", "2018-01-01 14:09:33", "testuser1", -1, 1, 1, false, 0, NULL),
  (-3, "3 Post", "2018-01-01 14:03:33", "testuser1", -2, 1, 0, false, 0, NULL),
  (-4, "11 Post", "2018-01-01 14:01:43", "testuser1", -1, 0, 0, false, 1, -1),
  (-5, "12 Post", "2018-01-01 14:05:33", "testuser1", -1, 0, 0, false, 1, -1),
  (-6, "21 Post", "2018-01-01 14:09:53", "testuser1", -1, 0, 0, false, 1, -2),
  (-7, "111 Post", "2018-01-01 14:00:33", "testuser1", -1, 0, 0, false, 2, -4);

INSERT INTO `discussment_user_post_reputation` (id, user_id, post_id, liked) VALUES
  (-1, "testuser1", -1, 1),
  (-2, "testuser2", -1, 1),
  (-3, "testuser1", -2, 1),
  (-4, "testuser2", -2, 0),
  (-5, "testuser1", -3, 1);
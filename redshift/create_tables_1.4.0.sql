CREATE TABLE communication_channel_dim(
    id BIGINT,
    canvas_id BIGINT,
    user_id BIGINT,
    address VARCHAR(256),
    type VARCHAR(256),
    position INTEGER,
    workflow_state VARCHAR(256),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
CREATE TABLE discussion_topic_fact(
    discussion_topic_id BIGINT,
    course_id BIGINT,
    enrollment_term_id BIGINT,
    course_account_id BIGINT,
    user_id BIGINT,
    assignment_id BIGINT,
    editor_id BIGINT,
    enrollment_rollup_id BIGINT,
    message_length INTEGER
);
CREATE TABLE assignment_dim(
    id BIGINT,
    canvas_id BIGINT,
    course_id BIGINT,
    title VARCHAR(256),
    description VARCHAR(0),
    due_at TIMESTAMP,
    unlock_at TIMESTAMP,
    lock_at TIMESTAMP,
    points_possible DOUBLE PRECISION,
    grading_type VARCHAR(256),
    submission_types VARCHAR(256),
    workflow_state VARCHAR(256),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    peer_review_count INTEGER,
    peer_reviews_due_at TIMESTAMP,
    peer_reviews_assigned BOOLEAN,
    peer_reviews BOOLEAN,
    automatic_peer_reviews BOOLEAN,
    all_day BOOLEAN,
    all_day_date TIMESTAMP,
    could_be_locked BOOLEAN,
    grade_group_students_individually BOOLEAN,
    anonymous_peer_reviews BOOLEAN,
    muted BOOLEAN,
    assignment_group_id BIGINT
);
CREATE TABLE quiz_question_group_dim(
    id BIGINT,
    canvas_id BIGINT,
    quiz_id BIGINT,
    name VARCHAR(256),
    position INTEGER,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
CREATE TABLE course_dim(
    id BIGINT,
    canvas_id BIGINT,
    root_account_id BIGINT,
    account_id BIGINT,
    enrollment_term_id BIGINT,
    name VARCHAR(256),
    code VARCHAR(256),
    type VARCHAR(256),
    created_at TIMESTAMP,
    start_at TIMESTAMP,
    conclude_at TIMESTAMP,
    publicly_visible BOOLEAN,
    sis_source_id VARCHAR(256),
    workflow_state VARCHAR(256),
    wiki_id BIGINT
);
CREATE TABLE requests(
    id VARCHAR(50),
    timestamp TIMESTAMP,
    timestamp_year VARCHAR(256),
    timestamp_month VARCHAR(256),
    timestamp_day VARCHAR(256),
    user_id BIGINT,
    course_id BIGINT,
    root_account_id BIGINT,
    course_account_id BIGINT,
    quiz_id BIGINT,
    discussion_id BIGINT,
    conversation_id BIGINT,
    assignment_id BIGINT,
    url VARCHAR(0),
    user_agent VARCHAR(0),
    http_method VARCHAR(256),
    remote_ip VARCHAR(256),
    interaction_micros BIGINT,
    web_application_controller VARCHAR(256),
    web_applicaiton_action VARCHAR(256),
    web_application_context_type VARCHAR(256),
    web_application_context_id VARCHAR(256)
);
CREATE TABLE quiz_submission_fact(
    score DOUBLE PRECISION,
    kept_score DOUBLE PRECISION,
    date TIMESTAMP,
    course_id BIGINT,
    enrollment_term_id BIGINT,
    course_account_id BIGINT,
    quiz_id BIGINT,
    assignment_id BIGINT,
    user_id BIGINT,
    submission_id BIGINT,
    enrollment_rollup_id BIGINT,
    quiz_submission_id BIGINT,
    quiz_points_possible DOUBLE PRECISION,
    score_before_regrade DOUBLE PRECISION,
    fudge_points DOUBLE PRECISION,
    total_attempts INTEGER,
    extra_attempts INTEGER,
    extra_time INTEGER,
    time_taken INTEGER
);
CREATE TABLE enrollment_fact(
    enrollment_id BIGINT,
    user_id BIGINT,
    course_id BIGINT,
    enrollment_term_id BIGINT,
    course_account_id BIGINT,
    course_section_id BIGINT,
    computed_final_score DOUBLE PRECISION,
    computed_current_score DOUBLE PRECISION
);
CREATE TABLE submission_dim(
    id BIGINT,
    canvas_id BIGINT,
    body VARCHAR(0),
    url VARCHAR(256),
    grade VARCHAR(256),
    submitted_at TIMESTAMP,
    submission_type VARCHAR(256),
    workflow_state VARCHAR(256),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    processed BOOLEAN,
    process_attempts INTEGER,
    grade_matches_current_submission BOOLEAN,
    published_grade VARCHAR(256),
    graded_at TIMESTAMP,
    has_rubric_assessment BOOLEAN,
    attempt INTEGER,
    has_admin_comment BOOLEAN,
    assignment_id BIGINT
);
CREATE TABLE submission_comment_participant_fact(
    submission_comment_participant_id BIGINT,
    submission_comment_id BIGINT,
    user_id BIGINT,
    submission_id BIGINT,
    assignment_id BIGINT,
    course_id BIGINT,
    enrollment_term_id BIGINT,
    course_account_id BIGINT,
    enrollment_rollup_id BIGINT
);
CREATE TABLE quiz_question_dim(
    id BIGINT,
    canvas_id BIGINT,
    quiz_id BIGINT,
    quiz_question_group_id BIGINT,
    position INTEGER,
    workflow_state VARCHAR(256),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    assessment_question_id BIGINT,
    assessment_question_version INTEGER,
    name VARCHAR(256),
    question_type VARCHAR(256),
    question_text VARCHAR(0),
    regrade_option VARCHAR(256),
    correct_comments VARCHAR(0),
    incorrect_comments VARCHAR(0),
    neutral_comments VARCHAR(0)
);
CREATE TABLE course_section_dim(
    id BIGINT,
    canvas_id BIGINT,
    name VARCHAR(256),
    course_id BIGINT,
    enrollment_term_id BIGINT,
    default_section BOOLEAN,
    accepting_enrollments BOOLEAN,
    can_manually_enroll BOOLEAN,
    start_at TIMESTAMP,
    end_at TIMESTAMP,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    workflow_state VARCHAR(256),
    restrict_enrollments_to_section_dates BOOLEAN,
    nonxlist_course_id BIGINT,
    sis_source_id VARCHAR(256)
);
CREATE TABLE wiki_dim(
    id BIGINT,
    canvas_id BIGINT,
    parent_type VARCHAR(256),
    title VARCHAR(0),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    front_page_url VARCHAR(0),
    has_no_front_page BOOLEAN
);
CREATE TABLE quiz_submission_historical_dim(
    id BIGINT,
    canvas_id BIGINT,
    quiz_id BIGINT,
    submission_id BIGINT,
    user_id BIGINT,
    version_number INTEGER,
    submission_state VARCHAR(256),
    workflow_state VARCHAR(256),
    quiz_state_during_submission VARCHAR(256),
    submission_scoring_policy VARCHAR(256),
    submission_source VARCHAR(256),
    has_seen_results VARCHAR(256),
    temporary_user_code VARCHAR(256),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    started_at TIMESTAMP,
    finished_at TIMESTAMP,
    due_at TIMESTAMP
);
CREATE TABLE conversation_message_dim(
    id BIGINT,
    canvas_id BIGINT,
    conversation_id BIGINT,
    author_id BIGINT,
    created_at TIMESTAMP,
    generated BOOLEAN,
    has_attachments BOOLEAN,
    has_media_objects BOOLEAN,
    body VARCHAR(0)
);
CREATE TABLE pseudonym_dim(
    id BIGINT,
    canvas_id BIGINT,
    user_id BIGINT,
    account_id BIGINT,
    workflow_state VARCHAR(256),
    last_request_at TIMESTAMP,
    last_login_at TIMESTAMP,
    current_login_at TIMESTAMP,
    last_login_ip VARCHAR(256),
    current_login_ip VARCHAR(256),
    position INTEGER,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    password_auto_generated BOOLEAN,
    deleted_at TIMESTAMP,
    sis_user_id VARCHAR(256),
    unique_name VARCHAR(256)
);
CREATE TABLE discussion_entry_dim(
    id BIGINT,
    canvas_id BIGINT,
    message VARCHAR(0),
    workflow_state VARCHAR(256),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    depth INTEGER
);
CREATE TABLE wiki_fact(
    wiki_id BIGINT,
    parent_course_id BIGINT,
    parent_group_id BIGINT,
    parent_course_account_id BIGINT,
    parent_group_account_id BIGINT,
    account_id BIGINT,
    root_account_id BIGINT,
    enrollment_term_id BIGINT,
    group_category_id BIGINT
);
CREATE TABLE enrollment_rollup_dim(
    id BIGINT,
    user_id BIGINT,
    course_id BIGINT,
    enrollment_count INTEGER,
    role_count INTEGER,
    base_role_count INTEGER,
    account_admin_role_count INTEGER,
    teacher_enrollment_role_count INTEGER,
    designer_enrollment_role_count INTEGER,
    ta_enrollment_role_count INTEGER,
    student_enrollment_role_count INTEGER,
    observer_enrollment_role_count INTEGER,
    account_membership_role_count INTEGER,
    no_permissions_role_count INTEGER,
    account_admin_enrollment_id BIGINT,
    teacher_enrollment_enrollment_id BIGINT,
    designer_enrollment_enrollment_id BIGINT,
    ta_enrollment_enrollment_id BIGINT,
    student_enrollment_enrollment_id BIGINT,
    observer_enrollment_enrollment_id BIGINT,
    account_membership_enrollment_id BIGINT,
    no_permissions_enrollment_id BIGINT,
    most_privileged_role VARCHAR(256),
    least_privileged_role VARCHAR(256)
);
CREATE TABLE submission_comment_fact(
    submission_comment_id BIGINT,
    submission_id BIGINT,
    recipient_id BIGINT,
    author_id BIGINT,
    assignment_id BIGINT,
    course_id BIGINT,
    enrollment_term_id BIGINT,
    course_account_id BIGINT,
    message_size_bytes INTEGER,
    message_character_count INTEGER,
    message_word_count INTEGER,
    message_line_count INTEGER
);
CREATE TABLE quiz_question_group_fact(
    quiz_question_group_id BIGINT,
    pick_count INTEGER,
    question_points DOUBLE PRECISION,
    quiz_id BIGINT,
    course_id BIGINT,
    assignment_id BIGINT,
    course_account_id BIGINT,
    enrollment_term_id BIGINT
);
CREATE TABLE assignment_fact(
    assignment_id BIGINT,
    course_id BIGINT,
    course_account_id BIGINT,
    enrollment_term_id BIGINT,
    points_possible DOUBLE PRECISION,
    peer_review_count INTEGER,
    assignment_group_id BIGINT
);
CREATE TABLE submission_comment_dim(
    id BIGINT,
    canvas_id BIGINT,
    submission_id BIGINT,
    recipient_id BIGINT,
    author_id BIGINT,
    assessment_request_id BIGINT,
    group_comment_id VARCHAR(256),
    comment VARCHAR(0),
    author_name VARCHAR(256),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    anonymous BOOLEAN,
    teacher_only_comment BOOLEAN,
    hidden BOOLEAN
);
CREATE TABLE wiki_page_fact(
    wiki_page_id BIGINT,
    wiki_id BIGINT,
    parent_course_id BIGINT,
    parent_group_id BIGINT,
    parent_course_account_id BIGINT,
    parent_group_account_id BIGINT,
    user_id BIGINT,
    account_id BIGINT,
    root_account_id BIGINT,
    enrollment_term_id BIGINT,
    group_category_id BIGINT,
    wiki_page_comments_count INTEGER,
    view_count INTEGER
);
CREATE TABLE course_ui_canvas_navigation_dim(
    id BIGINT,
    canvas_id BIGINT,
    name VARCHAR(256),
    default VARCHAR(256),
    original_position VARCHAR(256)
);
CREATE TABLE pseudonym_fact(
    pseudonym_id BIGINT,
    user_id BIGINT,
    account_id BIGINT,
    login_count INTEGER,
    failed_login_count INTEGER
);
CREATE TABLE enrollment_dim(
    id BIGINT,
    canvas_id BIGINT,
    root_account_id BIGINT,
    course_section_id BIGINT,
    role_id BIGINT,
    type VARCHAR(256),
    workflow_state VARCHAR(256),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    start_at TIMESTAMP,
    end_at TIMESTAMP,
    completed_at TIMESTAMP,
    self_enrolled BOOLEAN,
    sis_source_id VARCHAR(256),
    course_id BIGINT,
    user_id BIGINT
);
CREATE TABLE enrollment_term_dim(
    id BIGINT,
    canvas_id BIGINT,
    root_account_id BIGINT,
    name VARCHAR(256),
    date_start TIMESTAMP,
    date_end TIMESTAMP,
    sis_source_id VARCHAR(256)
);
CREATE TABLE course_ui_navigation_item_dim(
    id BIGINT,
    root_account_id BIGINT,
    visible VARCHAR(256),
    position INTEGER
);
CREATE TABLE quiz_question_answer_dim(
    id BIGINT,
    canvas_id BIGINT,
    quiz_question_id BIGINT,
    text VARCHAR(0),
    html VARCHAR(0),
    comments VARCHAR(0),
    text_after_answers VARCHAR(0),
    answer_match_left VARCHAR(256),
    answer_match_right VARCHAR(256),
    matching_answer_incorrect_matches VARCHAR(256),
    numerical_answer_type VARCHAR(256),
    blank_id VARCHAR(256),
    exact DOUBLE PRECISION,
    margin DOUBLE PRECISION,
    starting_range DOUBLE PRECISION,
    ending_range DOUBLE PRECISION
);
CREATE TABLE submission_fact(
    submission_id BIGINT,
    assignment_id BIGINT,
    course_id BIGINT,
    enrollment_term_id BIGINT,
    user_id BIGINT,
    grader_id BIGINT,
    course_account_id BIGINT,
    enrollment_rollup_id BIGINT,
    score DOUBLE PRECISION,
    published_score DOUBLE PRECISION,
    what_if_score DOUBLE PRECISION,
    submission_comments_count INTEGER
);
CREATE TABLE conversation_message_participant_fact(
    conversation_message_id BIGINT,
    conversation_id BIGINT,
    user_id BIGINT,
    course_id BIGINT,
    enrollment_term_id BIGINT,
    course_account_id BIGINT,
    group_id BIGINT,
    account_id BIGINT,
    enrollment_rollup_id BIGINT,
    message_size_bytes INTEGER,
    message_character_count INTEGER,
    message_word_count INTEGER,
    message_line_count INTEGER
);
CREATE TABLE group_fact(
    group_id BIGINT,
    parent_course_id BIGINT,
    parent_account_id BIGINT,
    parent_course_account_id BIGINT,
    enrollment_term_id BIGINT,
    max_membership INTEGER,
    storage_quota BIGINT,
    group_category_id BIGINT,
    account_id BIGINT,
    wiki_id BIGINT
);
CREATE TABLE submission_comment_participant_dim(
    id BIGINT,
    canvas_id BIGINT,
    participation_type VARCHAR(256),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
CREATE TABLE discussion_entry_fact(
    discussion_entry_id BIGINT,
    parent_discussion_entry_id BIGINT,
    user_id BIGINT,
    topic_id BIGINT,
    course_id BIGINT,
    enrollment_term_id BIGINT,
    course_account_id BIGINT,
    topic_user_id BIGINT,
    topic_assignment_id BIGINT,
    topic_editor_id BIGINT,
    enrollment_rollup_id BIGINT,
    message_length INTEGER
);
CREATE TABLE quiz_dim(
    id BIGINT,
    canvas_id BIGINT,
    root_account_id BIGINT,
    name VARCHAR(256),
    points_possible DOUBLE PRECISION,
    description VARCHAR(0),
    quiz_type VARCHAR(256),
    course_id BIGINT,
    assignment_id BIGINT,
    workflow_state VARCHAR(256),
    scoring_policy VARCHAR(256),
    anonymous_submissions VARCHAR(256),
    display_questions VARCHAR(256),
    answer_display_order VARCHAR(256),
    go_back_to_previous_question VARCHAR(256),
    could_be_locked VARCHAR(256),
    browser_lockdown VARCHAR(256),
    browser_lockdown_for_displaying_results VARCHAR(256),
    browser_lockdown_monitor VARCHAR(256),
    ip_filter VARCHAR(256),
    show_results VARCHAR(256),
    show_correct_answers VARCHAR(256),
    show_correct_answers_at TIMESTAMP,
    hide_correct_answers_at TIMESTAMP,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    published_at TIMESTAMP,
    unlock_at TIMESTAMP,
    lock_at TIMESTAMP,
    due_at TIMESTAMP,
    deleted_at TIMESTAMP
);
CREATE TABLE conversation_dim(
    id BIGINT,
    canvas_id BIGINT,
    has_attachments BOOLEAN,
    has_media_objects BOOLEAN,
    subject VARCHAR(256),
    course_id BIGINT,
    group_id BIGINT,
    account_id BIGINT
);
CREATE TABLE group_dim(
    id BIGINT,
    canvas_id BIGINT,
    name VARCHAR(256),
    description VARCHAR(0),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    is_public BOOLEAN,
    workflow_state VARCHAR(256),
    context_type VARCHAR(256),
    category VARCHAR(0),
    join_level VARCHAR(256),
    default_view VARCHAR(256),
    sis_source_id BIGINT,
    group_category_id BIGINT,
    account_id BIGINT,
    wiki_id BIGINT
);
CREATE TABLE quiz_submission_dim(
    id BIGINT,
    canvas_id BIGINT,
    quiz_id BIGINT,
    submission_id BIGINT,
    user_id BIGINT,
    workflow_state VARCHAR(256),
    quiz_state_during_submission VARCHAR(256),
    submission_scoring_policy VARCHAR(256),
    submission_source VARCHAR(256),
    has_seen_results VARCHAR(256),
    temporary_user_code VARCHAR(256),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    started_at TIMESTAMP,
    finished_at TIMESTAMP,
    due_at TIMESTAMP
);
CREATE TABLE external_tool_activation_dim(
    id BIGINT,
    canvas_id BIGINT,
    course_id BIGINT,
    account_id BIGINT,
    activation_target_type VARCHAR(256),
    url VARCHAR(4096),
    name VARCHAR(256),
    description VARCHAR(256),
    workflow_state VARCHAR(256),
    privacy_level VARCHAR(256),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    tool_id VARCHAR(256),
    selectable_all BOOLEAN
);
CREATE TABLE role_dim(
    id BIGINT,
    canvas_id BIGINT,
    root_account_id BIGINT,
    account_id BIGINT,
    name VARCHAR(256),
    base_role_type VARCHAR(256),
    workflow_state VARCHAR(256),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);
CREATE TABLE quiz_submission_historical_fact(
    score DOUBLE PRECISION,
    kept_score DOUBLE PRECISION,
    date TIMESTAMP,
    course_id BIGINT,
    enrollment_term_id BIGINT,
    course_account_id BIGINT,
    quiz_id BIGINT,
    assignment_id BIGINT,
    user_id BIGINT,
    submission_id BIGINT,
    enrollment_rollup_id BIGINT,
    quiz_submission_historical_id BIGINT,
    quiz_points_possible DOUBLE PRECISION,
    score_before_regrade DOUBLE PRECISION,
    fudge_points DOUBLE PRECISION,
    total_attempts INTEGER,
    extra_attempts INTEGER,
    extra_time INTEGER,
    time_taken INTEGER
);
CREATE TABLE communication_channel_fact(
    communication_channel_id BIGINT,
    user_id BIGINT,
    bounce_count INTEGER
);
CREATE TABLE quiz_fact(
    quiz_id BIGINT,
    points_possible DOUBLE PRECISION,
    time_limit INTEGER,
    allowed_attempts INTEGER,
    unpublished_question_count INTEGER,
    question_count INTEGER,
    course_id BIGINT,
    assignment_id BIGINT,
    course_account_id BIGINT,
    enrollment_term_id BIGINT
);
CREATE TABLE wiki_page_dim(
    id BIGINT,
    canvas_id BIGINT,
    title VARCHAR(256),
    body VARCHAR(0),
    workflow_state VARCHAR(256),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    url VARCHAR(0),
    protected_editing BOOLEAN,
    editing_roles VARCHAR(256),
    revised_at TIMESTAMP,
    could_be_locked BOOLEAN
);
CREATE TABLE course_ui_navigation_item_fact(
    root_account_id BIGINT,
    course_ui_navigation_item_id BIGINT,
    course_ui_canvas_navigation_id BIGINT,
    external_tool_activation_id BIGINT,
    course_id BIGINT,
    course_account_id BIGINT,
    enrollment_term_id BIGINT
);
CREATE TABLE account_dim(
    id BIGINT,
    canvas_id BIGINT,
    name VARCHAR(256),
    depth INTEGER,
    workflow_state VARCHAR(256),
    parent_account VARCHAR(256),
    parent_account_id BIGINT,
    grandparent_account VARCHAR(256),
    grandparent_account_id BIGINT,
    root_account VARCHAR(256),
    root_account_id BIGINT,
    subaccount1 VARCHAR(256),
    subaccount1_id BIGINT,
    subaccount2 VARCHAR(256),
    subaccount2_id BIGINT,
    subaccount3 VARCHAR(256),
    subaccount3_id BIGINT,
    subaccount4 VARCHAR(256),
    subaccount4_id BIGINT,
    subaccount5 VARCHAR(256),
    subaccount5_id BIGINT,
    subaccount6 VARCHAR(256),
    subaccount6_id BIGINT,
    subaccount7 VARCHAR(256),
    subaccount7_id BIGINT,
    subaccount8 VARCHAR(256),
    subaccount8_id BIGINT,
    subaccount9 VARCHAR(256),
    subaccount9_id BIGINT,
    subaccount10 VARCHAR(256),
    subaccount10_id BIGINT,
    subaccount11 VARCHAR(256),
    subaccount11_id BIGINT,
    subaccount12 VARCHAR(256),
    subaccount12_id BIGINT,
    subaccount13 VARCHAR(256),
    subaccount13_id BIGINT,
    subaccount14 VARCHAR(256),
    subaccount14_id BIGINT,
    subaccount15 VARCHAR(256),
    subaccount15_id BIGINT,
    sis_source_id VARCHAR(256)
);
CREATE TABLE group_membership_fact(
    group_id BIGINT,
    parent_course_id BIGINT,
    parent_account_id BIGINT,
    parent_course_account_id BIGINT,
    enrollment_term_id BIGINT,
    user_id BIGINT
);
CREATE TABLE discussion_topic_dim(
    id BIGINT,
    canvas_id BIGINT,
    title VARCHAR(256),
    message VARCHAR(0),
    type VARCHAR(256),
    workflow_state VARCHAR(256),
    last_reply_at TIMESTAMP,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    delayed_post_at TIMESTAMP,
    posted_at TIMESTAMP,
    deleted_at TIMESTAMP,
    discussion_type VARCHAR(256),
    pinned BOOLEAN,
    locked BOOLEAN
);
CREATE TABLE quiz_question_fact(
    quiz_question_id BIGINT,
    quiz_id BIGINT,
    quiz_question_group_id BIGINT,
    assessment_question_id BIGINT,
    course_id BIGINT,
    assignment_id BIGINT,
    course_account_id BIGINT,
    enrollment_term_id BIGINT,
    points_possible DOUBLE PRECISION
);
CREATE TABLE user_dim(
    id BIGINT,
    canvas_id BIGINT,
    root_account_id BIGINT,
    name VARCHAR(256),
    time_zone VARCHAR(256),
    created_at TIMESTAMP,
    visibility VARCHAR(256),
    school_name VARCHAR(256),
    school_position VARCHAR(256),
    gender VARCHAR(256),
    locale VARCHAR(256),
    public VARCHAR(256),
    birthdate TIMESTAMP,
    country_code VARCHAR(256),
    workflow_state VARCHAR(256),
    sortable_name VARCHAR(256)
);
CREATE TABLE quiz_question_answer_fact(
    quiz_question_answer_id BIGINT,
    quiz_question_id BIGINT,
    quiz_question_group_id BIGINT,
    quiz_id BIGINT,
    assessment_question_id BIGINT,
    course_id BIGINT,
    assignment_id BIGINT,
    course_account_id BIGINT,
    enrollment_term_id BIGINT,
    weight DOUBLE PRECISION,
    exact DOUBLE PRECISION,
    margin DOUBLE PRECISION,
    starting_range DOUBLE PRECISION,
    ending_range DOUBLE PRECISION
);
CREATE TABLE external_tool_activation_fact(
    external_tool_activation_id BIGINT,
    course_id BIGINT,
    account_id BIGINT,
    root_account_id BIGINT,
    enrollment_term_id BIGINT,
    course_account_id BIGINT
);

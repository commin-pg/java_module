CREATE DEFINER = `extcmsapp` @`%` PROCEDURE `spc_update_album_metadata`(
    pBarcode varchar(255),
    pAlbumCode varchar(255),
    pLabelCode varchar(255),
    pAlbumGenre varchar(255),
    pAlbumMainArtist varchar(255),
    pAlbumTitle varchar(255),
    pAlbumTitleLocal varchar(255)
) BEGIN declare dupCnt INTEGER default 0;

declare vLabelId INTEGER default 0;

/* 만약 SQL에러라면 ROLLBACK 처리한다. */
DECLARE EXIT HANDLER FOR SQLEXCEPTION BEGIN ROLLBACK;

SET
    RESULT = -1;

END;

/* 트랜젝션 시작 */
START TRANSACTION;

set
    @targetBarcode = '';

set
    @targetLabelCode = '';

set
    @targetAlbumGenre = '';

set
    @targetAlbumMainArtist = '';

set
    @targetTitle = '';

set
    @targetTitleLocal = '';

SELECT
    count(*) into dupCnt
FROM
    `release` r
    JOIN releases_albumcode ra ON r.album_code_id = ra.id
WHERE
    ra.code = pAlbumCode;

select
    id into vLabelId
from
    label
where
    label_code = 'L20210053';

IF dupCnt = 1
and vLabelId <> 0 THEN
SELECT
    @targetBarcode := r.barcode as 'UPC',
    @targetLabelCode := l.label_code as 'Label-Code',
    @targetAlbumGenre := gt.display_name AS 'Album-Genre',
    @targetAlbumMainArtist := ac.name as 'Album-Main-Artist',
    @targetTitle := r.title as 'Album-Title',
    @targetTitleLocal := r.title_local as 'Album-Title-Locale'
FROM
    `release` r
    JOIN releases_albumcode ra ON r.album_code_id = ra.id
    JOIN label_credit lc ON lc.id = r.label_credit_id
    JOIN label_credit_labels lcl ON lcl.labelcredit_id = lc.id
    JOIN label l ON l.id = lcl.label_id
    JOIN genre g ON g.id = r.genre_id
    JOIN genre_type gt ON gt.id = g.main_genre_id
    join artist_credit ac on ac.id = r.artist_credit_id
WHERE
    ra.code = pAlbumCode;

update
    `release` r
    JOIN releases_albumcode ra ON r.album_code_id = ra.id
    JOIN label_credit lc ON lc.id = r.label_credit_id
    JOIN label_credit_labels lcl ON lcl.labelcredit_id = lc.id --      JOIN
    --  label l ON l.id = lcl.label_id
    JOIN genre g ON g.id = r.genre_id
    JOIN genre_type gt ON gt.id = g.main_genre_id
    join artist_credit ac on ac.id = r.artist_credit_id
set
    r.barcode = '8809789958197',
    -- Barcode 수정 ==> r.barcode 변경
    r.title = 'No Matter What (Original Television Soundtrack) Pt. 35',
    -- Album-Title 수정  ==>  r.title 변경
    r.title_local = '누가 뭐래도 (Original Television Soundtrack) Pt. 35',
    -- Album-Title-Locale 수정 ==> r.title_local 변경
    r.genre_id = (
        SELECT
            g.id
        FROM
            extcms.genre g
            join genre_type gt on g.main_genre_id = gt.id
        where
            gt.display_name = 'Soundtrack'
            and alt_genre_id is null
        group by
            g.id
        limit
            1
    ), -- Album-Genre 수정 ==> r.genre_id 변경
    lcl.label_id = (
        select
            id
        from
            label
        where
            label_code = 'L20210277'
        limit
            1
    ) -- LabelCode 수정 ==>  r.label_credit_id 변경 (기존과 다를 시 Create And Update)
    -- Barcode 수정 ==> r.barcode 변경
    -- LabelCode 수정 ==>  r.label_credit_id 변경 (기존과 다를 시 Create And Update)
    -- Album-Genre 수정 ==> r.genre_id 변경
    -- artist 수정
    -- Album-Title 수정  ==>  r.title 변경
    -- Album-Title-Locale 수정 ==> r.title_local 변경
select
    dupCnt,
    @targetBarcode,
    @targetLabelCode,
    @targetAlbumGenre,
    @targetAlbumMainArtist,
    @targetTitle,
    @targetTitleLocal;

ELSE
select
    dupCnt,
    pAlbumTitle,
    pAlbumTitleLocal;

END IF;

/* 커밋 */
COMMIT;

SET
    RESULT = 0;

END
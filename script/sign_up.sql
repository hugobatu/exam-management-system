set search_path to exam_management_schema;

CREATE OR REPLACE FUNCTION usp_sign_up_account(
    p_email TEXT,
    p_phone TEXT,
    p_username TEXT,
    p_password TEXT,
    p_name TEXT,
    p_salt TEXT
)
RETURNS INTEGER
LANGUAGE plpgsql
AS $$
DECLARE
    v_count INTEGER;
BEGIN
    SELECT COUNT(*)
    INTO v_count
    FROM exam_management_schema.KhachHang
    WHERE email = p_email;

    IF v_count > 0 THEN
        RETURN 0; -- trung email
    END IF;

    SELECT COUNT(*)
    INTO v_count
    FROM exam_management_schema.KhachHang
    WHERE username = p_username;

    IF v_count > 0 THEN
        RETURN -1; -- trung username;
    END IF;
	
    SELECT COUNT(*)
    INTO v_count
    FROM exam_management_schema.KhachHang
    WHERE so_dien_thoai = p_phone;

    IF v_count > 0 THEN
        RETURN -2; -- trung sdt;
    END IF;


    BEGIN
        INSERT INTO exam_management_schema.Account(username, hashed_password, salt, account_type)
        VALUES (p_username, p_password, p_salt, 'KH');

        INSERT INTO exam_management_schema.KhachHang(ho_ten, so_dien_thoai, email, username)
        VALUES (p_name, p_phone, p_email, p_username);
		
        RETURN 1;
        
    EXCEPTION WHEN OTHERS THEN
        RAISE;
    END;
	
    return 1;
END;
$$;
